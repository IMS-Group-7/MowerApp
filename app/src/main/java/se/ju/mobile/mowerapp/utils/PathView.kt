package se.ju.mobile.mowerapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class PathView : View {
    private var pathPaint: Paint? = null
    private var path: Path? = null
    private var boundariesPaint: Paint? = null
    private var boundaries: Path? = null
    private var pathPoints: ArrayList<PointF> = ArrayList()

    private val scope = CoroutineScope(Dispatchers.Main)

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        pathPaint = Paint()
        pathPaint!!.color = Color.BLUE
        pathPaint!!.style = Paint.Style.STROKE
        pathPaint!!.strokeWidth = 5F
        path = Path()
        boundariesPaint = Paint()
        boundariesPaint!!.color = Color.BLACK
        boundariesPaint!!.style = Paint.Style.STROKE
        boundariesPaint!!.strokeWidth = 7F
        boundaries = Path()

        scope.launch {
            try {
                fetchAndDrawBoundaries()
                fetchAndDrawPath()
            } catch (e: Exception) {
                Log.e("BoundaryDrawing", "Failed to fetch boundary points", e)
            }
        }
    }

    private suspend fun fetchAndDrawBoundaries() {
        val boundariesPoints = withContext(Dispatchers.IO) {
            val res = ApiManager()
            val asyncResponse =
                res.makeHttpGetRequest("http://34.173.248.99/coordinates/boundaries")
            val jsonResponse = JSONArray(asyncResponse)
            val boundariesPoints = ArrayList<PointF>()

            for (i in 0 until jsonResponse.length()) {
                boundariesPoints.add(
                    PointF(
                        jsonResponse.getJSONObject(i).getString("x").toFloat(),
                        jsonResponse.getJSONObject(i).getString("y").toFloat(),
                    )
                )
            }
            boundariesPoints
        }

        for (i in 0 until boundariesPoints.size) {
            val point: PointF = boundariesPoints[i]
            Log.d("DEBUG", "$point")

            if (i == 0) {
                boundaries?.moveTo(point.x, point.y)
            } else {
                boundaries?.lineTo(point.x, point.y)
            }
        }
        invalidate()
    }

    private suspend fun fetchAndDrawPath() {
        while (true) {
            pathPoints = withContext(Dispatchers.IO) {
                val res = ApiManager()
                val asyncResponse =
                    res.makeHttpGetRequest("http://34.173.248.99/coordinates/active-session-path")
                val jsonResponse = JSONArray(asyncResponse)
                val pathPoints = ArrayList<PointF>()

                for (i in 0 until jsonResponse.length()) {
                    pathPoints.add(
                        PointF(
                            jsonResponse.getJSONObject(i).getString("x").toFloat(),
                            jsonResponse.getJSONObject(i).getString("y").toFloat()
                        )
                    )
                }
                pathPoints
            }

            path = Path()
            for (i in 0 until pathPoints.size) {
                val point: PointF = pathPoints[i]
                if (i == 0) {
                    path?.moveTo(point.x, point.y)
                } else {
                    path?.lineTo(point.x, point.y)
                }

                delay(5000)
                invalidate()
            }
        }

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: android.graphics.Canvas?) {
        super.onDraw(canvas)

        val pathPoints = ArrayList<PointF>()

        if (boundaries != null) {
            canvas?.drawPath(boundaries!!, boundariesPaint!!)
        }

        for (i in 0 until pathPoints.size) {
            val point: PointF = pathPoints[i]
            if (i == 0) {
                path?.moveTo(point.x, point.y)
            } else {
                path?.lineTo(point.x, point.y)
            }
        }
        canvas?.drawPath(path!!, pathPaint!!)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope.cancel()
    }

}
