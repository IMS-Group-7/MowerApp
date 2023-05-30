package se.ju.mobile.mowerapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.runBlocking
import org.json.JSONArray

class PathView : View {
    private var pathPaint: Paint? = null
    private var boundariesPaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private var boundaries: Path? = null
    private var lastPathPoints: ArrayList<PointF> = ArrayList<PointF>()

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
        pathPaint!!.color = Color.rgb(137, 171, 65)
        pathPaint!!.style = Paint.Style.STROKE
        pathPaint!!.strokeWidth = 15F
        pathPaint!!.strokeCap = Paint.Cap.ROUND
        pathPaint!!.strokeJoin = Paint.Join.ROUND
        pathPaint!!.setPathEffect(CornerPathEffect(50f))

        boundariesPaint = Paint()
        boundariesPaint!!.color = Color.BLACK
        boundariesPaint!!.style = Paint.Style.STROKE
        boundariesPaint!!.strokeWidth = 7F
        boundaries = Path()

        backgroundPaint = Paint()
        backgroundPaint!!.color = Color.rgb(31, 44, 71)
        backgroundPaint!!.style = Paint.Style.FILL
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: android.graphics.Canvas?) {
        super.onDraw(canvas)
        var pathPoints = ArrayList<PointF>()
        val boundariesPoints = ArrayList<PointF>()
        val res = ApiManager()
        var bounds = RectF()
        val boundsScaleMatrix = Matrix()
        val padValue = 40f
        var scalar: Float
        val path = Path()

        // We redraw and retrive every second
        postInvalidateDelayed(500)

        width

        // Retrieve data from backend
        runBlocking {
            try {
                val asyncResponse =
                    res.makeHttpGetRequest("http://34.173.248.99/coordinates/active-session-path")
                val jsonResponse = JSONArray(asyncResponse)

                for (i in 0 until jsonResponse.length()) {
                    pathPoints.add(
                        PointF(
                            jsonResponse.getJSONObject(i).getString("x").toFloat(),
                            jsonResponse.getJSONObject(i).getString("y").toFloat()
                        )
                    )
                }

                lastPathPoints = pathPoints
            } catch (e: java.lang.RuntimeException) {
                pathPoints = lastPathPoints
            }
        }

        // Plot the path
        for (i in 0 until pathPoints.size) {
            val point: PointF = pathPoints[i]

            var scaledPoint = PointF(
                point.x,
                point.y
            )

            if (i == 0) {
                path?.moveTo(scaledPoint.x, scaledPoint.y)
            } else {
                path?.lineTo(scaledPoint.x, scaledPoint.y)
            }
        }

        // Compute bounding box of path and center
        path?.computeBounds(bounds, true)

        // Center it
        boundsScaleMatrix.setTranslate(
            -bounds.centerX() + width  / 2,
            -bounds.centerY() + height / 2
        )

        path?.transform(boundsScaleMatrix)

        // Derive scalar
        val scalarX = (width - padValue * 2) / bounds.width()
        val scalarY = (height - padValue * 2) / bounds.height()

        if (scalarX <= scalarY) {
            scalar = scalarX
        } else if (scalarY < scalarX) {
            scalar = scalarY
        } else {
            scalar = 1f
        }

        path?.computeBounds(bounds, true)

        // Scale the path to the screen
        boundsScaleMatrix.setScale(
            scalar,
            -scalar,
            bounds.centerX(),
            bounds.centerY()
        )

        path?.transform(boundsScaleMatrix)

        // Finally draw it
        canvas?.drawPaint(this.backgroundPaint!!)
        canvas?.drawPath(path!!, this.pathPaint!!)
    }
}
