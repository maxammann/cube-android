package lm;

/**
 * Represents a Main
 */
public class Main {
//    public static final DateTimeFormatter HOUR = new DateTimeFormatterBuilder()
//            .appendValue(ChronoField.HOUR_OF_DAY, 2).toFormatter();
//
//    public static final DateTimeFormatter MINUTE = new DateTimeFormatterBuilder()
//            .appendValue(ChronoField.MINUTE_OF_HOUR, 2).toFormatter();
//    public static String TEXT = "Fuck";
//
//    public static void main(String[] args) throws IOException {
//
//
//        System.out.println("Default font: " + args[0]);
//
//        RGB red = new RGB(128, 0, 0);
//        RGB blue = new RGB(0, 0, 128);
//
//        GPIO.init();
//
//        LedMatrix matrix = new LedMatrix(32, 32, 4);
//        FontLibrary fontLibrary = new FontLibrary();
//
//        FontLibrary.Font font = fontLibrary.newFont(args[0], 17);
//
//        LedMatrixThread thread = new LedMatrixThread(matrix, LedMatrixThread.DEFAULT_BASE_TIME_NANOS);
//        thread.start();
//
//        BufferedReader bufferedInput = new BufferedReader(new InputStreamReader(System.in));
//
//        get("/pause", (req, res) -> {
//            thread.pause();
//            return "Paused";
//        });
//
//        get("/unpause", (req, res) -> {
//            thread.unpause();
//            return "Unpaused";
//        });
//
//        get("/hello/:text", (request, response) -> {
//            TEXT = request.params(":text");
//            return request.params(":text");
//        });
//
//        while (true) {
//            if (bufferedInput.ready()) {
//                bufferedInput.readLine();
//                break;
//            }
//
//            LocalTime time = LocalTime.now();
//            String hour = HOUR.format(time);
//            String minute = MINUTE.format(time);
//
//            matrix.clear();
//
//            matrix.renderString(font, TEXT, 0, 0, blue);
//
//            LedMatrixString hourString = new LedMatrixString();
//            LedMatrixString minuteString = new LedMatrixString();
//
//            hourString.populate(hour, font);
//            minuteString.populate(minute, font);
//
//            int hourHeight = hourString.getHeight();
//            int minuteHeight = minuteString.getHeight();
//
//            int height = minuteHeight + hourHeight + 4;
//
//            int starty = (32 - height) / 2;
//
//            hourString.renderHorizontalCentered(matrix, starty, red);
//            minuteString.renderHorizontalCentered(matrix, starty + hourHeight + 4, red);
//
//            hourString.free();
//            minuteString.free();
//
//            matrix.swapBuffers();
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        Spark.stop();
//
//        thread.stop();
//
//        font.free();
//        fontLibrary.free();
//        matrix.free();
//        thread.free();
//    }

    //        BufferedImage read = ImageIO.read(new File("img.png"));
//        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
//        AffineTransform at = new AffineTransform();
//        at.scale(0.16, 0.16);
//        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//        img = scaleOp.filter(read, img);
//
//        for (int x = 0; x < img.getHeight(); x++) {
//            for (int y = 0; y < img.getWidth(); y++) {
//                int[] rgb = getPixelData(img, x, y);
//
//                LmLibrary.rgb_ color = new LmLibrary.rgb_();
//
//                color.r = (byte) rgb[0];
//                color.g = (byte) rgb[1];
//                color.b = (byte) rgb[2];
//
//                lm.lm_matrix_set_pixel(matrix, (short) x, (short) y, color);
//            }
//        }
//
//        lm.lm_fonts_print_string(library, matrix, "test", font, (short) 0, (short) 2, color);
//        lm.lm_fonts_font_free(library, font);


//    private static int[] getPixelData(BufferedImage img, int x, int y) {
//        int argb = img.getRGB(x, y);
//
//        return new int[]{
//                (argb >> 16) & 0xff, //red
//                (argb >> 8) & 0xff, //green
//                (argb) & 0xff  //blue
//        };
//    }
}
