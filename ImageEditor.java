import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEditor {
    /* Constants (Magic numbers) */
    private static final String PNG_FORMAT = "png";
    private static final String NON_RGB_WARNING =
            "Warning: we do not support the image you provided. \n" +
            "Please change another image and try again.";
    private static final String RGB_TEMPLATE = "(%3d, %3d, %3d) ";
    private static final int BLUE_BYTE_SHIFT = 0;
    private static final int GREEN_BYTE_SHIFT = 8;
    private static final int RED_BYTE_SHIFT = 16;
    private static final int ALPHA_BYTE_SHIFT = 24;
    private static final int BLUE_BYTE_MASK = 0xff << BLUE_BYTE_SHIFT;
    private static final int GREEN_BYTE_MASK = 0xff << GREEN_BYTE_SHIFT;
    private static final int RED_BYTE_MASK = 0xff << RED_BYTE_SHIFT;
    private static final int ALPHA_BYTE_MASK = ~(0xff << ALPHA_BYTE_SHIFT);

    /* Static variables - DO NOT add any additional static variables */
    static int[][] image;

    /**
     * Open an image from disk and return a 2D array of its pixels.
     * Use 'load' if you need to load the image into 'image' 2D array instead
     * of returning the array.
     *
     * @param pathname path and name to the file, e.g. "input.png",
     *                 "D:\\Folder\\ucsd.png" (for Windows), or
     *                 "/User/username/Desktop/my_photo.png" (for Linux/macOS).
     *                 Do NOT use "~/Desktop/xxx.png" (not supported in Java).
     * @return 2D array storing the rgb value of each pixel in the image
     * @throws IOException when file cannot be found or read
     */
    public static int[][] open(String pathname) throws IOException {
        BufferedImage data = ImageIO.read(new File(pathname));
        if (data.getType() != BufferedImage.TYPE_3BYTE_BGR &&
                data.getType() != BufferedImage.TYPE_4BYTE_ABGR) {
            System.err.println(NON_RGB_WARNING);
        }
        int[][] array = new int[data.getHeight()][data.getWidth()];

        for (int row = 0; row < data.getHeight(); row++) {
            for (int column = 0; column < data.getWidth(); column++) {
                /* Images are stored by column major
                   i.e. (2, 10) is the pixel on the column 2 and row 10
                   However, in class, arrays are in row major
                   i.e. [2][10] is the 11th element on the 2nd row
                   So we reverse the order of i and j when we load the image.
                 */
                array[row][column] = data.getRGB(column, row) & ALPHA_BYTE_MASK;
            }
        }

        return array;
    }

    /**
     * Load an image from disk to the 'image' 2D array.
     *
     * @param pathname path and name to the file, see open for examples.
     * @throws IOException when file cannot be found or read
     */
    public static void load(String pathname) throws IOException {
        image = open(pathname);
    }

    /**
     * Save the 2D image array to a PNG file on the disk.
     *
     * @param pathname path and name for the file. Should be different from
     *                 the input file. See load for examples.
     * @throws IOException when file cannot be found or written
     */
    public static void save(String pathname) throws IOException {
        BufferedImage data = new BufferedImage(
                image[0].length, image.length, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < data.getHeight(); row++) {
            for (int column = 0; column < data.getWidth(); column++) {
                // reverse it back when we write the image
                data.setRGB(column, row, image[row][column]);
            }
        }
        ImageIO.write(data, PNG_FORMAT, new File(pathname));
    }

    /**
     * Unpack red byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return red value in that packed pixel, 0 <= red <= 255
     */
    private static int unpackRedByte(int rgb) {
        return (rgb & RED_BYTE_MASK) >> RED_BYTE_SHIFT;
    }

    /**
     * Unpack green byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return green value in that packed pixel, 0 <= green <= 255
     */
    private static int unpackGreenByte(int rgb) {
        return (rgb & GREEN_BYTE_MASK) >> GREEN_BYTE_SHIFT;
    }

    /**
     * Unpack blue byte from a packed RGB int
     *
     * @param rgb RGB packed int
     * @return blue value in that packed pixel, 0 <= blue <= 255
     */
    private static int unpackBlueByte(int rgb) {
        return (rgb & BLUE_BYTE_MASK) >> BLUE_BYTE_SHIFT;
    }

    /**
     * Pack RGB bytes back to an int in the format of
     * [byte0: unused][byte1: red][byte2: green][byte3: blue]
     *
     * @param red   red byte, must satisfy 0 <= red <= 255
     * @param green green byte, must satisfy 0 <= green <= 255
     * @param blue  blue byte, must satisfy 0 <= blue <= 255
     * @return packed int to represent a pixel
     */
    private static int packInt(int red, int green, int blue) {
        return (red << RED_BYTE_SHIFT)
                + (green << GREEN_BYTE_SHIFT)
                + (blue << BLUE_BYTE_SHIFT);
    }

    /**
     * Print the current image 2D array in (red, green, blue) format.
     * Each line represents a row in the image.
     */
    public static void printImage() {
        for (int[] ints : image) {
            for (int pixel : ints) {
                System.out.printf(
                        RGB_TEMPLATE,
                        unpackRedByte(pixel),
                        unpackGreenByte(pixel),
                        unpackBlueByte(pixel));
            }
            System.out.println();
        }
    }
    private static void rotate90(){
        //This helper method rotates the 2D array clockwise by 90 ddegrees.
        int[][] temp = new int[image[0].length][image.length];
        for (int i=0;i<image.length;++i){
            for (int j = 0;j<image[i].length;++j){
                temp[j][image.length-1-i] = image[i][j];
            }
        }
        image = temp;
    }
    public static void rotate(int degree){
        //This method rotates the 2D aray clockwise by degree degrees using rotate90()
        if (degree<0 ||degree%90!=0){
            return;
        }
        else{
            degree = degree%360;
        }
        if (degree==0){
            return;
        }
        else if (degree == 90){
            rotate90();
            return;
        }
        else if (degree == 180){
            rotate90();
            rotate90();
            return;
        }
        else if (degree == 270){
            rotate90();
            rotate90();
            rotate90();
            return;
        }
    }
    private static int average(int[][] t,int heightScale, int widthScale){
        //this is a helper method which is used to find the average of a 2D int array
        //this method outputs an int packaged in the RGB format
        int sumRed = 0;
        int sumGreen=0;
        int sumBlue=0;
        for (int i=0;i<t.length;i++){
            for (int j=0;j<t[0].length;j++){
                sumRed+=unpackRedByte(t[i][j]);
                sumGreen+=unpackGreenByte(t[i][j]);
                sumBlue+=unpackBlueByte(t[i][j]);
            }
        }
        sumRed = sumRed/(heightScale*widthScale);
        sumGreen = sumGreen/(heightScale*widthScale);
        sumBlue = sumBlue/(heightScale*widthScale);
        return(packInt(sumRed, sumGreen, sumBlue));
    }
    public static void downSample(int heightScale, int widthScale){
        //This method shrinks an image to a smaller resolution
        if (image.length%heightScale!=0){
            return;
        }
        else if (image[0].length%widthScale!=0){
            return;
        }
        else if (heightScale<1 || widthScale<1){
            return;
        }
        else if (heightScale==1 && widthScale==1){
            return;
        }
        int[][] temp = new int[image.length/heightScale][image[0].length/widthScale];
        int[][] t = new int[heightScale][widthScale];
        for (int i=0;i<image.length;i+=heightScale){
            for (int j=0;j<image[0].length;j+=widthScale){
                //this is where I want this loop to make smaller sub arrays
                for (int r = 0;r<heightScale;r++){
                    for (int c = 0;c<widthScale;c++){
                        t[r][c]=image[i+r][j+c];
                    }
                }
                temp[i/heightScale][j/widthScale]=average(t,heightScale,widthScale);
            }
        }
        image=temp;
    }
    public static int patch(int startRow,int startColumn, int[][]
    patchImage, int transparentRed, int transparentGreen, int
    transparentBlue){
        //this method patches an image on top of another image
        int pixels = 0;
        int patchHeight = patchImage.length;
        int patchWidth = patchImage[0].length;
        if(startRow<0||startColumn<0){
            return pixels;
        }
        else if(startRow>image.length||startColumn>image[0].length){
            return pixels;
        }
        else if(startRow+patchHeight>image.length){
            return pixels;
        }
        else if(startColumn+patchWidth>image[0].length){
            return pixels;
        }
        for (int i=startRow;i<startRow+patchHeight;i++){
            for (int j=startColumn;j<startColumn+patchWidth;j++){
                if (patchImage[i-startRow][j-startColumn]==packInt(transparentRed, 
                transparentGreen, transparentBlue)){
                    continue;
                }
                image[i][j] = patchImage[i-startRow][j-startColumn];
                pixels++;
            }
        }

        return pixels;
    }
}
