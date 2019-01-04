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
<font color="#0099ff"  size=4>2. Triangles</font></br>
<font color="#0099ff"  size=4>3. Triangles</font></br>
<font color="#0099ff"  size=4>4. Triangles</font></br>

## Primitives Assembly