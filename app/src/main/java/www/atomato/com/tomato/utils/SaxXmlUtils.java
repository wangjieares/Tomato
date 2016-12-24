package www.atomato.com.tomato.utils;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import www.atomato.com.tomato.data.GroupItem;

/**
 * Created by wangjie on 12/24/2016.
 * <GroupName>
 * <读书>
 * <时间></时间>
 * <状态></状态>
 * <提醒></提醒>
 * <进度></进度>
 * <总数></总数>
 * <总分钟></总分钟>
 * </读书>
 * </GroupName>
 */

public class SaxXmlUtils {
    /**
     * Sax方式，创建 XML
     */
    private static GroupItem groupItem;

    public static void save(List<GroupItem> items, OutputStream outStream, String groupName)
            throws Exception, IllegalStateException, IOException {
        XmlSerializer serializer = Xml.newSerializer();//获取XML写入信息的序列化对象
        serializer.setOutput(outStream, "UTF-8");//设置要写入的OutputStream
        serializer.startDocument("UTF-8", true);//设置文档标签
        serializer.startTag(null, groupName);//设置开始标签，第一个参数为namespace
        for (GroupItem item : items) {
            serializer.startTag(null, "item");
            serializer.startTag(null, "title");
            serializer.text(item.getTitle());
            serializer.endTag(null, "title");
//            serializer.attribute(null, "id", String.valueOf(item.getId()));
            serializer.startTag(null, "time");
            serializer.text(item.getTime() + "");
            serializer.endTag(null, "time");
            serializer.startTag(null, "progress");
            serializer.text(item.getCreate() + "");
            serializer.endTag(null, "progress");
            serializer.startTag(null, "create");
            serializer.text(item.getProgress() + "");
            serializer.endTag(null, "create");
            serializer.endTag(null, "item");
        }
        serializer.endTag(null, groupName);
        serializer.endDocument();
        outStream.flush();
        outStream.close();
    }

    public static List<GroupItem> parse(InputStream inputStream, String title) throws Exception {
        List<GroupItem> mList = new ArrayList<>();
        // 由android.util.Xml创建一个XmlPullParser实例
        XmlPullParser xpp = Xml.newPullParser();
        // 设置输入流 并指明编码方式
        xpp.setInput(inputStream, "UTF-8");
        // 产生第一个事件
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                // 判断当前事件是否为文档开始事件
                case XmlPullParser.START_DOCUMENT:
                    break;
                // 判断当前事件是否为标签元素开始事件
                case XmlPullParser.START_TAG:
                    LogUtils.e("START_TAG",xpp.getName());
                    if (xpp.getName().equals("item")) {
                        groupItem = new GroupItem("", 0, 0, 0, 0);
                    } else if (xpp.getName().equals("title")) {
                        xpp.nextText();
                        groupItem.setTitle(xpp.getText());
                    } else if (xpp.getName().equals("time")) {
                        xpp.nextText();
                        groupItem.setTime(Integer.valueOf(xpp.getText()));
                    } else if (xpp.getName().equals("progress")) {
                        xpp.nextText();
                        groupItem.setTime(Integer.valueOf(xpp.getText()));
                    } else if (xpp.getName().equals("create")) {
                        xpp.nextText();
                        groupItem.setTime(Integer.valueOf(xpp.getText()));
                        mList.add(groupItem);//读取到最后放入
                        LogUtils.e("Sax START_TAG", groupItem.toString());
                    }
                    break;

                // 判断当前事件是否为标签元素结束事件
                case XmlPullParser.END_TAG:
                    break;
            }
            // 进入下一个元素并触发相应事件
            eventType = xpp.next();
        }
        return mList;
    }

}
