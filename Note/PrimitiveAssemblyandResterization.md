#引言
本章节描述了图元类型和OpenGL ES支持的几何对象，并且解释了如何绘制它们。主要介绍Primitive assembly和Rasterization。

**<font color="#00ffff" face="黑体" size=5>1. 图元装配阶段</font>**</br>
<P>发生在一个图元的顶点被vertex shader处理之后。</br>
图元装配阶段执行Clipping、perspective divide、 viewport transformation操作。</p>

**<font color="#00ffff" face="黑体" size=5>2. 光栅化</font>**</br>
光栅化是把图元转换成二维的片段。片段代表像素。

##Primitives
一个图元是一个几何对象。图元由一组表示顶点位置的顶点表示。</br>
一般属性：颜色、纹理、法向量等</br>
图元绘制命令：
```
GLES30.glDrawArrays(int mode, int first, int n);
GLES30.glDrawElements();
GLES30.glDrawRangeElements();
GLES30.glDrawArraysInstanced();
GLES30.glDrawElementsInstanced();
```
<font color="#0099ff" face="黑体" size=4>1. Triangles</font></br>
[D:\pole7lynn\openglesDemo\openglesdemo-tutorial\Note\Image\Triangles.png]
<font color="#0099ff" face="黑体" size=4>2. Lines</font></br>
<font color="#0099ff" face="黑体" size=4>3. Points sprites</font></br>