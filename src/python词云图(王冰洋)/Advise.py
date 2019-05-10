# - * - coding: utf - 8 -*-

from os import path
from scipy.misc import imread
import matplotlib.pyplot as plt
import jieba
from wordcloud import WordCloud, ImageColorGenerator


class generateCiYunAdvice():
    # 获取当前文件路径
    d = path.dirname(__file__)

    stopwords = {}
    isCN = 1  # 默认启用中文分词
    back_coloring_path = "111.png"  # 设置背景图片路径
    text_path = 'basicdata.txt'  # 设置要分析的文本路径
    font_path = '经典综艺体简.ttf'  # 为matplotlib设置中文字体路径没
    stopwords_path = 'stopwords.txt'  # 停用词词表
    imgname1 = "Advice.png"  # 保存的图片名字1(只按照背景图片形状)

    back_coloring = imread(path.join(d, back_coloring_path))  # 设置背景图片

    # 设置词云属性
    wc = WordCloud(font_path=font_path,  # 设置字体
                   background_color="white",  # 背景颜色
                   max_words=1000,  # 词云显示的最大词数
                   mask=back_coloring,  # 设置背景图片
                   max_font_size=150,  # 字体最大值
                   random_state=42,
                   width=1000, height=860, margin=2,  # 设置图片默认的大小,但是如果使用背景图片的话,那么保存的图片大小将会按照其大小保存,margin为词语边缘距离
                   )

    def jiebaclearText(text, stopwords_path, d):
        mywordlist = []
        seg_list = jieba.cut(text, cut_all=False)
        liststr = "/ ".join(seg_list)
        f_stop = open(path.join(d, stopwords_path))
        try:
            f_stop_text = f_stop.read()
        finally:
            f_stop.close()

        f_stop_seg_list = f_stop_text.split('\n')
        for myword in liststr.split('/'):
            if not (myword.strip() in f_stop_seg_list) and len(myword.strip()) > 1:
                mywordlist.append(myword)
        return ''.join(mywordlist)

    if __name__ == '__main__':

        # 打开文件，每行读取并存入text中做分词
        file = open(path.join(d, text_path))
        text = ""
        while file.readline():
            stringArray = file.readline().split('|')
            if len(stringArray) == 10:
                text = text + stringArray[8]
        file.close()

        if isCN:
            text = jiebaclearText(text, stopwords_path, d)

        # 生成词云
        wc.generate(text)
        # 从背景图片生成颜色值
        image_colors = ImageColorGenerator(back_coloring)

        plt.figure()
        # 以下代码显示图片
        plt.imshow(wc)
        plt.axis("off")
        plt.show()
        # 绘制词云

        # 保存图片
        wc.to_file(path.join(d, imgname1))
