package dsaa.lab01;

public class Drawer {
    private static void drawLine(int n, char ch) {
        // print a sequence of chars of length n
        for (int i = 0; i < n; i++) {
            System.out.print(ch);
        }
    }

    public static void drawPyramid(int n, int margin) {
        // draw each line of pyramid of height n
        for (int i = 0; i < n; i++) {
            drawLine(margin, '.');
            drawLine(n - i - 1, '.');
            drawLine(2 * i + 1, 'X');
            drawLine(n - i - 1, '.');
            drawLine(margin, '.');
            System.out.println();
        }
    }

    public static void drawPyramid(int n) {
        // draw each line of pyramid of height n
        drawPyramid(n, 0);
    }

    public static void drawChristmassTree(int n) {
        // draw pyramids from height j: 1 to n
        for (int j = 1; j <= n; j++) {
            // draw each pyramid with margin
            drawPyramid(j, n-j);
        }
    }
    public static void drawRectangle(int n) {
        drawLine(n, 'x');
        System.out.println();
        for (int i = 0; i < n-2; i++) {
            drawLine(1, 'x');
            drawLine(n-2, '.');
            drawLine(1, 'x');
            System.out.println();
        }
        drawLine(n, 'x');
        System.out.println();
    }

//    public static void drawPyramid(int n) {
//        // draw each line of pyramid of height n
//        for (int i = 0; i < n; i++) {
//            drawLine(n - i - 1, '.');
//            drawLine(2 * i + 1, 'X');
//            drawLine(n - i - 1, '.');
//            System.out.println();
//        }
//    }
//
//    public static void drawChristmassTree(int n) {
//        // draw pyramids from height j: 1 to n
//        for (int j = 1; j <= n; j++) {
//            // draw each line of pyramid of height i: 0 to j-1
//            for (int i = 0; i < j; i++) {
//                // number of dots: width-(2*i+1)
//                // width being 2*(n-1)+1 = 2n - 2 + 1 = 2n - 1
//                // so: 2n - 1 - 2i - 1 = 2n - 2i - 2 = 2 * (n-i-1)
//                drawLine(n - i - 1, '.');
//                drawLine(2 * i + 1, 'X');
//                drawLine(n - i - 1, '.');
//                System.out.println();
//            }
//        }
//    }

}
