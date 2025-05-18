package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.dto.BookDescriptionDTO;
import Application.SpringBootAudioBookApplication.dto.LibriVoxBookDTO;
import Application.SpringBootAudioBookApplication.services.interfaces.XMLServiceInterface;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLService implements XMLServiceInterface {

    private static final String LOCAL_XML_PATH = "data/librivox.xml";
    private static final String REMOTE_API_URL = "https://librivox.org/api/feed/audiobooks?format=xml";

    @Override
    public List<LibriVoxBookDTO> findAllInLocalXmlByTitle(String title) {
        List<LibriVoxBookDTO> result = new ArrayList<>();
        try {
            Document doc = loadXml();
            NodeList books = doc.getElementsByTagName("book");
            for (int i = 0; i < books.getLength(); i++) {
                Node node = books.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) node;
                    String bookName = getElementText(bookElement, "title");
                    if (bookName != null && bookName.equalsIgnoreCase(title)) {
                        String idText = getElementText(bookElement, "id");
                        int id = idText != null ? Integer.parseInt(idText) : 0;
                        String authorFullName = "";
                        NodeList authorNodes = bookElement.getElementsByTagName("author");
                        if (authorNodes.getLength() > 0 && authorNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
                            Element authorElement = (Element) authorNodes.item(0);
                            String firstName = getElementText(authorElement, "first_name");
                            String lastName = getElementText(authorElement, "last_name");
                            authorFullName = ((firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "")).trim();
                        }
                        LibriVoxBookDTO dto = new LibriVoxBookDTO();
                        dto.setId(id);
                        dto.setBookName(bookName);
                        dto.setAuthorName(authorFullName);
                        result.add(dto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<LibriVoxBookDTO> findAllInLocalXmlByAuthor(String author) {
        List<LibriVoxBookDTO> result = new ArrayList<>();
        try {
            Document doc = loadXml();
            NodeList books = doc.getElementsByTagName("book");
            for (int i = 0; i < books.getLength(); i++) {
                Node node = books.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element bookElement = (Element) node;
                    String authorFullName = "";
                    NodeList authorNodes = bookElement.getElementsByTagName("author");
                    if (authorNodes.getLength() > 0 && authorNodes.item(0).getNodeType() == Node.ELEMENT_NODE) {
                        Element authorElement = (Element) authorNodes.item(0);
                        String firstName = getElementText(authorElement, "first_name");
                        String lastName = getElementText(authorElement, "last_name");
                        authorFullName = ((firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "")).trim();
                    }
                    if (authorFullName.equalsIgnoreCase(author)) {
                        String title = getElementText(bookElement, "title");
                        String idText = getElementText(bookElement, "id");
                        int id = idText != null ? Integer.parseInt(idText) : 0;
                        LibriVoxBookDTO dto = new LibriVoxBookDTO();
                        dto.setId(id);
                        dto.setBookName(title);
                        dto.setAuthorName(authorFullName);
                        result.add(dto);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<LibriVoxBookDTO> findInLocalXmlByTag(String tagName, String value) {
        List<LibriVoxBookDTO> result = new ArrayList<>();
        try {
            Document doc = loadXml();
            NodeList books = doc.getElementsByTagName("book");
            for (int i = 0; i < books.getLength(); i++) {
                Element book = (Element) books.item(i);
                String tagValue = getText(book, tagName);
                if (tagValue != null && tagValue.equalsIgnoreCase(value)) {
                    result.add(parseBookElement(book));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateLocalXml() {
        try (InputStream in = new URL(REMOTE_API_URL).openStream()) {
            Files.copy(in, Paths.get(LOCAL_XML_PATH), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Document loadXml() throws Exception {
        File xmlFile = new File(LOCAL_XML_PATH);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(xmlFile);
    }

    private String getText(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        return nodes.getLength() > 0 ? nodes.item(0).getTextContent() : null;
    }

    private LibriVoxBookDTO parseBookElement(Element element) {
        LibriVoxBookDTO dto = new LibriVoxBookDTO();
        dto.setBookName(getText(element, "title"));
        dto.setAuthorName(getText(element, "author"));
        dto.setLanguage(getText(element, "language"));
        dto.setGenre(getText(element, "genre"));
        dto.setUrlLibriVox(getText(element, "url"));
        return dto;
    }

    @Override
    public BookDescriptionDTO findInLocalXmlById(int id) {
        try {
            Document doc = loadXml();
            NodeList books = doc.getElementsByTagName("book");
            for (int i = 0; i < books.getLength(); i++) {
                Element bookElement = (Element) books.item(i);
                String idText = getElementText(bookElement, "id");
                if (idText != null && Integer.parseInt(idText) == id) {
                    String description = getElementText(bookElement, "description");
                    String imageUrl = "https://m.media-amazon.com/images/I/31f3O2Eg6OL._AC_UL800_QL65_.png";
                    return (new BookDescriptionDTO(description, imageUrl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0 && nodes.item(0).getTextContent() != null) {
            return nodes.item(0).getTextContent().trim();
        }
        return "";
    }
}
