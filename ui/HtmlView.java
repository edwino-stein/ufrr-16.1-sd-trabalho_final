package ui;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTML;
import javax.swing.text.StyleConstants;
import javax.swing.text.Element;
import ui.Component;

public abstract class HtmlView extends Component {

    public static final int BEFORESTART = 0;
    public static final int AFTERSTART = 1;
    public static final int BEFOREEND = 2;
    public static final int AFTEREND = 3;

    protected String basicSnippet = "<html><head></head><body></body></html>";
    protected static String contentTypes = "text/html";
    protected JEditorPane textView;
    protected HTMLDocument document;

    protected void init(){

        this.textView = new JEditorPane(HtmlView.contentTypes, this.basicSnippet);
        this.textView.setEditable(false);
        this.textView.setBounds(this.x, this.y, this.width, this.height);

        this.document = (HTMLDocument) this.textView.getDocument();

        this.compObj = new JScrollPane(this.textView);
        this.updatePos();
    }

    public HTMLDocument getDocument(){
        return this.document;
    }

    public Element getBody(){
        return this.getElementByTagName(HTML.Tag.BODY);
    }

    public Element getElementByTagName(HTML.Tag tag){

        Element[] roots = this.document.getRootElements();
        Element el = null;

        for(int i = 0; i < roots[0].getElementCount(); i++){
            Element element = roots[0].getElement(i);
            if(element.getAttributes().getAttribute( StyleConstants.NameAttribute ) == tag) {
                el = element;
                break;
            }
        }

        return el;
    }

    public boolean append(Element el, String snippet, int order){

        HTMLDocument document = this.getDocument();

        try{

            switch(order){
                case HtmlView.BEFORESTART:
                    document.insertBeforeStart(el, snippet);
                break;

                case HtmlView.AFTERSTART:
                    document.insertAfterStart(el, snippet);
                break;

                case HtmlView.AFTEREND:
                    document.insertAfterEnd(el, snippet);
                break;

                case HtmlView.BEFOREEND:
                default:
                    document.insertBeforeEnd(el, snippet);
                break;
            }

        } catch(Exception e){
            return false;
        }

        return true;
    }

    public boolean append(Element el, String snippet){
        return this.append(el, snippet, HtmlView.BEFOREEND);
    }

    public boolean setInnerHTML(Element el, String snippet){

        HTMLDocument document = this.getDocument();

        try{
            document.setInnerHTML(el, snippet);
        } catch(Exception e){
            return false;
        }

        return true;
    }

    public void resetDocument(){
        this.textView.setText(this.basicSnippet);
    }

    public JScrollPane getJComponent(){
        return (JScrollPane) this.compObj;
    }
}
