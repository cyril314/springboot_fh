package com.fit.common.servlet;

import com.fit.config.shiro.Jurisdiction;
import com.fit.util.Const;
import org.apache.shiro.session.Session;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = {"/code", "/code.do"})
public class SecurityCodeServlet extends HttpServlet {

    private static final long serialVersionUID = -1L;

    public static final String CODE = "123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ";
    public static final int CODE_LENGTH = CODE.length();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = drawImg(output);
        Session session = Jurisdiction.getSession();
        session.setAttribute(Const.SESSION_SECURITY_CODE, code);
        output.writeTo(response.getOutputStream());
    }

    private String drawImg(ByteArrayOutputStream output) throws IOException {
        String code = randomChar(4);
        int width = 50;
        int height = 25;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66, 2, 82);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) baseY);
        g.dispose();
        ImageIO.write(bi, "jpg", output);
        return code;
    }

    private String randomChar(int count) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(CODE.charAt(r.nextInt(CODE_LENGTH)));
        }
        return sb.toString();
    }
}
