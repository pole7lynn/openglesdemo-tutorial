# 引言
本章节描述了图元类型和OpenGL ES支持的几何对象，并且解释了如何绘制它们。主要介绍Primitive assembly和Rasterization。

**<font color="#00ffff" face="黑体" size=5>1. 图元装配阶段</font>**</br>
<P>发生在一个图元的顶点被vertex shader处理之后。</br>
图元装配阶段执行Clipping、perspective divide、 viewport transformation操作。</p>

**<font color="#00ffff" face="黑体" size=5>2. 光栅化</font>**</br>
光栅化是把图元转换成二维的片段。片段代表像素。

## Primitives
一个图元是一个几何对象。图元由一组表示顶点位置的顶点表示。</br>
一般属性：颜色、纹理、法向量等</br>
图元绘制命令：
```
GLES30.glDrawArrays(int mode, int first, int count);//count为顶点个数
GLES30.glDrawElements();
GLES30.glDrawRangeElements();
GLES30.glDrawArraysInstanced();
GLES30.glDrawElementsInstanced();
```
<font color="#0099ff" face="黑体" size=4>1. Triangles</font></br>
![Triangles](https://github.com/pole7lynn/openglesdemo-tutorial/blob/master/Note/Image/Triangles.png)

<font color="#0099ff" face="黑体" size=4>2. Lines</font></br>
![Lines](https://github.com/pole7lynn/openglesdemo-tutorial/blob/master/Note/Image/Lines.png)

*<font color="#0044ff" face="黑体" size=3>指定线宽</font></br>
```
GLES30.glLineWidth(2.0f);
```

*<font color="#0044ff" face="黑体" size=3>查询线宽范围</font></br>
```
float[] lineWidthRange = new float[2];
GLES30.glGetFloatv(GLES30.GL_ALIASED_LINE_WIDTH_RANGE, lineWidthRange, 0);
Log.i(TAG, "min = " + lineWidthRange[0] + ", max = " + lineWidthRange[1]);
```

<font color="#0099ff" face="黑体" size=4>3. Points sprites</font></br>
*<font color="#0044ff" face="黑体" size=3>指定点的大小</font></br>
**in shader
gl_PointSize = 2.0f;
**in program
```
float[] pointSizeRange = new float[2];
GLES30.glGetFloatv(GLES30.GL_ALIASED_POINT_SIZE_RANGE, pointSizeRange, 0);
Log.i(TAG, "min = " + pointSizeRange[0] + ", max = " + pointSizeRange[1]);
```

*<font color="#ff000" face="黑体" size=3>Note:</font>*Window的坐标原点(0,0)在(Left, bottom), 而对于point sprites来说，坐标原点(0,0)在(left,top)。关于具体的OpenGL ES里面坐标系的问题请参考[坐标系详解]()。

## Drawing Primitives
<font color="#0099ff" size=4>1. Drawing commonds</font></br>
*<font color="#ff0000" size=3> glDrawArrays();</font>
```
glDrawArrays(int mode,int first,int count);
//mode:GL_POINTS,GL_LINES,GL_LINE_STRIP,GL_LINE_LOOP,GL_TRIANGLES,GL_TRIANGLE_STRIP,GL_TRIANGLE_FAN
//first:specifies the starting vertex index in the enabled vertex arrays.
//count: specifies the number of vertices to be drawn.
```

*<font color="#ff0000" size=3> glDrawElements();</font>
```
glDrawElements(int mode,int count,int type,int offset);
glDrawElements(int mode,int count,int type,java.nio.Buffer indices);
glDrawRangeElements(int mode,int start,int end,int count,int type,java.nio.Buffer indices);
glDrawRangeElements(int mode,int start,int end,int count,int type,int offset);
//mode:same with glDrawArrays();
//count:specifies the number of indices to be drawn
//type:GL_UNSIGNED_BYTE,GL_UNSIGNED_SHORT,GL_UNSIGNED_INT
//indices:
//start:specifies the minimum array index in indices
//end:specifies the maximum array index in indices
```
* <font color="#ff0000"  size=4>glDrawRangeElements()</font></br>
```
glDrawRangeElements(int mode, int start, int end, int count, int type, BUffer indices);
glDrawRangeElements(int mode, int start, int end, int count, int type, int offest);
//start: specifies the minimum array index in indices
//end: specifies the maximum array index in indices
```
<font color="#0099ff" size=4>2. Primitive restart</font></br>
<font color="#0099ff" size=4>3. Provoking vertex</font></br>
<font color="#0099ff" size=4>4. Geometry instance</font></br>

## Primitives Assembly
![Primitives Assembly](https://github.com/pole7lynn/openglesdemo-tutorial/blob/master/Note/Image/primitiveAssenblyStage.PNG)

<font color="#0099ff" size=4>** Coordinate Systems</font></br>
![Coordinate Systems](https://github.com/pole7lynn/openglesdemo-tutorial/blob/master/Note/Image/CoordinateSystems.PNG)

<font color="#0099ff" size=4>1. Clipping</font></br>


<font color="#0099ff" size=4>2. Perspective division</font></br>
把经过裁剪的点进行转换成归一化的设备坐标。
(Xc/W,Yc/w,Zc/w)

<font color="#0099ff" size=4>3. Viewport transformation</font></br>
```
GLES30.glViewport(x,y,w,h);
//(x,y)左下角
```
归一化设备坐标转换成屏幕坐标：
xw = xd * w/2 + Ox;
yw = yd * h/2 + Oy;
zw = zd * (f-n)/2 + (f+n)/2;

##Rasterization
Rasterization pipline:</br>
![Coordinate Systems](https://github.com/pole7lynn/openglesdemo-tutorial/blob/master/Note/Image/Rasterization.PNG)

在片段着色器，可以通过很多的操作来控制图元的光栅化。</br>
<font color="#0099ff" size=4>1. Culling(剔除操作)</font></br>
剔除操作丢弃背对查看器的图元。
```
   private void enableCulling() {
        GLES30.glEnable(GLES30.GL_CULL_FACE);
        //mode:GL_BACK,GL_FRONT, GL_FRONT_AND_BACK
        GLES30.glCullFace(GLES30.GL_BACK);
        GLES30.glFrontFace(GLES30.GL_CW);//mode:GL_CW, GL_CCW
    }
    private void diasbleCulling() {
        GLES30.glDisable(GLES30.GL_CULL_FACE);
    }
```

<font color="#0099ff" size=4>2. Polygon offest</font></br>
因为精度限制将会产生artifacts:Z-fighting artifacts.
为了避免锯齿，在深度测试和深度值写入深度缓冲区之前需要添加一个delta去计算深度值。
如果深度测试测过了，那么原来的深度值会被写入深度缓冲区。
```
private void polygonOffest() {
        float polygonOffestFactor = -1.0f;
        float polygonOffestUnits = -2.0f;
        GLES30.glEnable(GLES30.GL_POLYGON_OFFSET_FILL);
        GLES30.glPolygonOffset(polygonOffestFactor, polygonOffestUnits);
    }
```
depth offest = m * factor + r * units;

<font color="#0099ff" size=4>3. Occlusion Queries(遮挡查询)</font></br>
```
private void occlusionQuery() {
        int[] ids = new int[1];
        GLES30.glGenQueries(1, ids, 0);
        GLES30.glBeginQuery(GLES30.GL_ANY_SAMPLES_PASSED, ids[0]);
        GLES30.glEndQuery(GLES30.GL_ANY_SAMPLES_PASSED);
    }
```