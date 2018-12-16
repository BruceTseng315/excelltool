# excelltool
excell to
1.准备文件（见目录src/main/resource/input）：包括FDD、LTE、退服记录、report模板文件、昨日输出的三天统计表。
2.主程序在ProcessMain.java。首先用init函数初始化，主要功能是配置输入文件的路径和输出excell、word的路径
3.主要功能大部分已实现，缺少的是输出word表中较“昨日变化”的几个字段，可以通过读取昨日word记录进行处理之后来进行补充。
4.一些问题：1）FDD、LTE表不是很干净，比如LTE表中出现了本应不出现在FDD中的记录2）部分基站没有对应的地址信息，这些异常数据可以在程序中稍加改造后输出来，可以进一步进行处理。3）部分小区名称比如KES88-EFH_NBES88-ENH-2找不到对应的基站信息
