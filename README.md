# 模版打印组件
支持打印,支持输出为svg,pdf,image.
```json
[
    {
        "layers":[
          {"type":"图层类型","x":"x坐标","y":"y坐标","width":200,"heigth":100,"color":"16进制颜色,如:#4d99bf"}
        ]
    }
]
```

```java
 List<Pager> pagers = new JsonPageBuilder().build(json);
 List<String> svgs = PrinterUtils.printToSvg(pagers);
 
```

图层列表:

|   图层类型    |   type  |     属性列表   |
| -------------: |:-------------:| :--------------:|
| 文字        | text | x,y,fontFamily,fontSize,color,width,height,align |
| 线       | line    |  x1,y1,x2,y2,color |
| 矩形       | rect    |  x,y,width,height,color    |
| 图片       | img    |  x,y,width,height,imgType,imgData |

属性说明:

| 属性       | 说明           | 支持图层 |
| -------------: |:-------------:| :----------: |
| x        | x坐标 |  除线外全部 |
| y       | y坐标    |  除线外全部   |
| x1       | 线的横向起点坐标    | 线  |
| x2       | 线的横向终点坐标,x2-x1则为线的长度    | 线  |
| y1       | 线的纵向起点坐标    | 线  |
| y2       | 线的纵向终点坐标,y1==y2则为直线    |  线  |
| width       | 宽度    | 矩形,图片,文字 |
| height       | 高度    |  矩形,图片,文字   |
| text       | 文字内容    | 文字 |
| align       | 文字居中方式,both,center,left,right    | 文字 |
| fontFamily       | 字体,不支持指定多个字体    | 文字|
| fontSize       | 字体大小   | 文字 |
| color       | 颜色,格式为16进制:#4d99bf  | 除图片外全部|
| imgType       | 图片类型    |  图片   |
| imgData       | 图片数据(格式见`图片格式表`)    |  图片  |

图片格式表:

| imgType       |  说明           | imgData |
| -------------: |:-------------:| :---------- |
| static        | 静态文件地址,支持本地,远程,base64图片 |  `file:D:/imags/test.png` , `http://server/test.png` ,`base64字符串`  |
| qrCode  | 二维码    |  字符串,如: abc ,将会生成对应的二维码图片   |
| barCode  | 条形码    |  和二维码相同,注意:不支持中文   |
