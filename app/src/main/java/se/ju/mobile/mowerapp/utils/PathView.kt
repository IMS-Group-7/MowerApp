package se.ju.mobile.mowerapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import kotlin.math.cos
import kotlin.math.sin

class PathView : View {
    private var pathPaint: Paint? = null
    private var path: Path? = null
    private var boundariesPaint: Paint? = null
    private var boundaries: Path? = null

    private var number: Float = 0f

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

        number = 20f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: android.graphics.Canvas?) {
        super.onDraw(canvas)
        val pathPoints = ArrayList<PointF>()
        val boundariesPoints = ArrayList<PointF>()
        val res = ApiManager()

        val paintUse = this.pathPaint

        postInvalidateDelayed(10)

        number += 0.1f

        canvas.apply {
            paintUse?.let { this?.drawCircle(500f + sin(number.toDouble()).toFloat()*200f,
                300f + cos(number.toDouble()/4f).toFloat()*200f, 35f, it) }
        }

//        invalidate()

        // Draws the boundaries
//        runBlocking {
//            val asyncResponse = res.makeHttpGetRequest("http://34.173.248.99/coordinates/boundaries")
//            val jsonResponse = JSONArray(asyncResponse)
//            for (i in 0 until jsonResponse.length()) {
//                boundariesPoints.add(
//                    PointF(
//                        jsonResponse.getJSONObject(i).getString("x").toFloat(),
//                        jsonResponse.getJSONObject(i).getString("y").toFloat()
//                    )
//                )
//            }
//            for (i in 0 until boundariesPoints.size) {
//                val point: PointF = boundariesPoints[i]
//                if (i == 0) {
//                    boundaries?.moveTo(point.x, point.y)
//                } else {
//                    boundaries?.lineTo(point.x, point.y)
//                }
//            }
//            canvas?.drawPath(boundaries!!, boundariesPaint!!)
//        }
//
//        // Draw the path
//        for (i in 0 until pathPoints.size) {
//            val point: PointF = pathPoints[i]
//            if (i == 0) {
//                path?.moveTo(point.x, point.y)
//            } else {
//                path?.lineTo(point.x, point.y)
//            }
//        }
//        canvas?.drawPath(path!!, pathPaint!!)
    }
}
