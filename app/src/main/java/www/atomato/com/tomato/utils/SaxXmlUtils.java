package www.atomato.com.tomato.utils;

import android.util.Xml;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import www.atomato.com.tomato.data.Person;

/**
 * Created by wangjie on 12/24/2016.
 */

public class SaxXmlUtils {
    /** Sax方式，创建 XML  */
    public static void save(List<Person> persons, OutputStream outStream)
            throws Exception, IllegalStateException, IOException {
        XmlSerializer serializer = Xml.newSerializer();//获取XML写入信息的序列化对象
        serializer.setOutput(outStream, "UTF-8");//设置要写入的OutputStream
        serializer.startDocument("UTF-8", true);//设置文档标签
        serializer.startTag(null, "persons");//设置开始标签，第一个参数为namespace
        for (Person person : persons) {
            serializer.startTag(null, "person");
            serializer.attribute(null, "id", String.valueOf(person.getId()));
            serializer.startTag(null, "name");
            serializer.text(person.getName());
            serializer.endTag(null, "name");
            serializer.startTag(null, "age");
            serializer.text(person.getAge());
            serializer.endTag(null, "age");
            serializer.endTag(null, "person");
        }
        serializer.endTag(null, "persons");
        serializer.endDocument();
        outStream.flush();
        outStream.close();
    }
}
