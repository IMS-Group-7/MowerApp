package se.ju.mobile.mowerapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
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
    private var pathPoints: ArrayList<PointF> = ArrayList<PointF>()

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
//        path = Path()
        boundariesPaint = Paint()
        boundariesPaint!!.color = Color.BLACK
        boundariesPaint!!.style = Paint.Style.STROKE
        boundariesPaint!!.strokeWidth = 7F
        boundaries = Path()

        number = 20f

        pathPoints = ArrayList<PointF>()
        pathPoints.add(PointF(9f, 15f))
        pathPoints.add(PointF(24f, 17f))
        pathPoints.add(PointF(27f, 25f))
        pathPoints.add(PointF(35f, 15f))
        pathPoints.add(PointF(40f, 20f))
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: android.graphics.Canvas?) {
        super.onDraw(canvas)
//        val pathPoints = ArrayList<PointF>()
        val pathPoints = this.pathPoints
        val boundariesPoints = ArrayList<PointF>()
        val res = ApiManager()
        val bounds = RectF()
        val boundsScaleMatrix = Matrix()
        val path = Path()
        var padValue = 40f
        var scalar = 1f
        val pathPaint = this.pathPaint

        postInvalidateDelayed(500)

        // Draw the path
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

        // Compute bounding box of path and derive scalar
        path?.computeBounds(bounds, true)

        if (bounds.width() > bounds.height()) {
            scalar = (width - padValue * 2) / bounds.width()
        } else {
            scalar = (height - padValue * 2) / bounds.height()
        }

        // Scale the path to the screen
        boundsScaleMatrix.setScale(
            scalar,
            -scalar,
            bounds.centerX(),
            bounds.centerY()
        )

        path?.transform(boundsScaleMatrix)

        // Center it
        boundsScaleMatrix.setTranslate(
            -bounds.centerX() + width  / 2,
            -bounds.centerY() + height / 2
        )

        path?.transform(boundsScaleMatrix)

        // Finally draw
        canvas.apply {
            this?.drawPath(path!!, pathPaint!!)
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
