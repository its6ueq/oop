//package object;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//public class texture {
//    public Image originTex;
//    texture () {
//        originTex = new ImageIcon (texture.class.getResource("/texture/texture.png")).getImage();
//        if (originTex != null) {
//            System.out.println("Image loaded successfully.");
//        } else {
//            System.out.println("Failed to load the image.");
//        }
//        public BufferedImage extractTankImage() {
//            BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g2d = bufferedImage.createGraphics();
//            g2d.drawImage(originTex, 0, 0, 100, 100, 0, 0, 100, 100, null);
//            g2d.dispose();
//        }
//
//    }
//}
