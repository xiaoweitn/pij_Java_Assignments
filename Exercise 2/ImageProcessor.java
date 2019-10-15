package com.bham.pij.exercises.e2a;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;


/*
 * DO NOT IMPORT ANYTHING ELSE.
 */

public class ImageProcessor extends Application {

    // You can change these values if you want, to get a smaller or larger Window.
    private static final int STAGE_WIDTH = 400;
    private static final int STAGE_HEIGHT = 400;    

    // These are the filters you must implement.
    private static final String[] filterTypes = {"IDENTITY","BLUR", "SHARPEN", "EMBOSS", "EDGE"};
    
    private Image image;
    private ImageView imgv;
    private VBox vbox;
    private Scene scene;
    private ArrayList<MenuItem> menuItems;
    private String currentFilename;
    
    public ImageProcessor() {
        
    }
    
    /*
     * You must complete the next four methods. You do not need to change
     * any other methods.
     */
    
    // You must complete this method.
    public Color[][] applyFilter(Color[][] pixels, float[][] filter) {

		Color[][] newImage = new Color[pixels.length - 2][pixels[0].length - 2];

			for (int i = 1; i < pixels.length - 1; i++) { //starts from the centre

				for (int j = 1; j < pixels[i].length - 1; j++) {	// iterates through the rows and columns	

					double[][] Red = new double[3][3]; //creates an array for the RGB values to be stored
					double[][] Green = new double[3][3];
					double[][] Blue = new double[3][3];	

					int m = 0;

					for (int a = i - 1; a < i + 2; a++) { // forces the RGB values into a 3x3 array

						int n = 0;

						for (int b = j - 1; b < j + 2; b++) {

							Red[m][n] = pixels[a][b].getRed();

							Green[m][n] = pixels[a][b].getGreen();

							Blue[m][n] = pixels[a][b].getBlue();

							n++;
						}	

						m++;			
					}

					double newRed = 0;
					double newGreen = 0;
					double newBlue = 0;

					for (int a = 0; a < filter.length; a++) {

						for(int b = 0; b < filter.length; b++) {

							newRed += (Red[a][b] * filter[a][b]);
							newGreen += (Green[a][b] * filter[a][b]);
							newBlue += (Blue[a][b] * filter[a][b]);							
						}
					}
					if (newRed >= 1) {

						newRed = 1;
					}
					if (newGreen >= 1) {

						newGreen = 1;
					}
					if (newBlue >= 1) {

						newBlue = 1;
					}
					if (newRed <= 0) {
					
						newRed = 0;
					}
					if (newGreen <= 0) {

						newGreen = 0;
					}
					if (newBlue <= 0) {

						newBlue = 0;
					}		
					Color temp = new Color(newRed, newGreen, newBlue, 1.0);
					newImage[i - 1][j - 1] = temp;
				}	
			}
        return newImage;
    }

    // You must complete this method.
    public float[][] createFilter(String filterType) {

		if (filterType.equalsIgnoreCase("Identity")) {

			float[][] Identity = new float[3][3];
		
			Identity[0][0] = 0;
			Identity[0][1] = 0;
			Identity[0][2] = 0;
			Identity[1][0] = 0;
			Identity[1][1] = 1;
			Identity[1][2] = 0;
			Identity[2][0] = 0;
			Identity[2][1] = 0;
			Identity[2][2] = 0;

				return Identity;
		}

		if (filterType.equalsIgnoreCase("Blur")) {

			float[][] Blur = new float[3][3];

			Blur[0][0] = 0.0625f;
			Blur[0][1] = 0.125f;
			Blur[0][2] = 0.0625f;
			Blur[1][0] = 0.125f;
			Blur[1][1] = 0.25f;
			Blur[1][2] = 0.125f;
			Blur[2][0] = 0.0625f;
			Blur[2][1] = 0.125f;
			Blur[2][2] = 0.0625f;

				return Blur;
		}

		if (filterType.equalsIgnoreCase("Sharpen")) {

			float[][] Sharpen = new float[3][3];

			Sharpen[0][0] = 0;
			Sharpen[0][1] = -1;
			Sharpen[0][2] = 0;
			Sharpen[1][0] = -1;
			Sharpen[1][1] = 5;
			Sharpen[1][2] = -1;
			Sharpen[2][0] = 0;
			Sharpen[2][1] = -1;
			Sharpen[2][2] = 0;

				return Sharpen;
		}

		if (filterType.equalsIgnoreCase("Edge")) {

			float[][] Edge = new float[3][3];

			Edge[0][0] = -1;
			Edge[0][1] = -1;
			Edge[0][2] = -1;
			Edge[1][0] = -1;
			Edge[1][1] = 8;
			Edge[1][2] = -1;
			Edge[2][0] = -1;
			Edge[2][1] = -1;
			Edge[2][2] = -1;

				return Edge;
		}

		if (filterType.equalsIgnoreCase("Emboss")) {

			float[][] Emboss = new float[3][3];

			Emboss[0][0] = -2;
			Emboss[0][1] = -1;
			Emboss[0][2] = 0;
			Emboss[1][0] = -1;
			Emboss[1][1] = 0;
			Emboss[1][2] = 1;
			Emboss[2][0] = 0;
			Emboss[2][1] = 1;
			Emboss[2][2] = 2;

				return Emboss;
		}

        return null;
    }

    // You must complete this method.
    public Color[][] applySepia(Color[][] pixels) {

    Color[][] newImage = new Color[pixels.length][pixels[0].length];

			for (int i = 0; i < pixels.length; i++) { //starts from the first pixel

				for (int j = 0; j < pixels[i].length; j++) { //iterates through the rows and columns		

					double red = pixels[i][j].getRed();
					double green = pixels[i][j].getGreen();
					double blue = pixels[i][j].getBlue();

					double newRed = 0;
					double newGreen = 0;
					double newBlue = 0;	

					newRed = (red * 0.393 + green * 0.769 + blue * 0.189);
					newGreen = (red * 0.349 + green * 0.686 + blue * 0.168);
					newBlue = (red * 0.272 + green * 0.534 + blue * 0.131);								
			

					if (newRed >= 1) {

						newRed = 1;
					}
					if (newGreen >= 1) {

						newGreen = 1;
					}
					if (newBlue >= 1) {

						newBlue = 1;
					}
					if (newRed <= 0) {
						
						newRed = 0;
					}
					if (newGreen <= 0) {

						newGreen = 0;
					}
					if (newBlue <= 0) {

						newBlue = 0;
					}

					Color temp = new Color(newRed, newGreen, newBlue, 1.0);
					newImage[i][j] = temp;
				}
			}
        return newImage;        
    }

    // You must complete this method.
    public Color[][] applyGreyscale(Color[][] pixels) {

    	Color[][] newImage = new Color[pixels.length][pixels[0].length];

			for (int i = 0; i < pixels.length; i++) { //starts from the first pixel

				for (int j = 0; j < pixels[i].length; j++) { //iterates through the rows and columns		

					double red = pixels[i][j].getRed();
					double green = pixels[i][j].getGreen();
					double blue = pixels[i][j].getBlue();

					double newRed = 0;
					double newGreen = 0;
					double newBlue = 0;	

					newRed = newGreen = newBlue = ((red + green + blue)/3);							
			

					if (newRed >= 1) {

						newRed = 1;
					}
					if (newGreen >= 1) {

						newGreen = 1;
					}
					if (newBlue >= 1) {

						newBlue = 1;
					}
					if (newRed <= 0) {
						
						newRed = 0;
					}
					if (newGreen <= 0) {

						newGreen = 0;
					}
					if (newBlue <= 0) {

						newBlue = 0;
					}
							
					Color temp = new Color(newRed, newGreen, newBlue, 1.0);
					newImage[i][j] = temp;
				}
			}
        return newImage;        
    }
    
    /*
     * 
     * You can ignore the methods below.
     * 
     */

    public void filterImage(String filterType) {

        Color[][] pixels = getPixelDataExtended();

        float[][] filter = createFilter(filterType);
        
        Color[][] filteredImage = applyFilter(pixels, filter);

        WritableImage wimg = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());

        PixelWriter pw = wimg.getPixelWriter();

        for (int i = 0; i < wimg.getHeight(); i++) {
            for (int j = 0; j < wimg.getWidth(); j++) {
                pw.setColor(i, j, filteredImage[i][j]);
            }
        }
        
        File newFile = new File("filtered_" + filterType + "_" + this.currentFilename);
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wimg, null), "png", newFile);
        } catch (Exception s) {
        }

        initialiseVBox(false);

        image = wimg;
        imgv = new ImageView(wimg);
        vbox.getChildren().add(imgv);
    }
    
    private void sepia() {
        
        Color[][] pixels = getPixelData();
        
        Color[][] newPixels = applySepia(pixels);
        
        WritableImage wimg = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());

        PixelWriter pw = wimg.getPixelWriter();

        for (int i = 0; i < wimg.getHeight(); i++) {
            for (int j = 0; j < wimg.getWidth(); j++) {
                pw.setColor(i, j, newPixels[i][j]);
            }
        }
        
        File newFile = new File("filtered_SEPIA_" + this.currentFilename);
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wimg, null), "png", newFile);
        } catch (Exception s) {
        }

        initialiseVBox(false);

        image = wimg;
        imgv = new ImageView(wimg);
        vbox.getChildren().add(imgv);
    }
    
    private void greyscale() {
        Color[][] pixels = getPixelData();
        
        Color[][] newPixels = applyGreyscale(pixels);
        
        WritableImage wimg = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());

        PixelWriter pw = wimg.getPixelWriter();

        for (int i = 0; i < wimg.getHeight(); i++) {
            for (int j = 0; j < wimg.getWidth(); j++) {
                pw.setColor(i, j, newPixels[i][j]);
            }
        }
        
        File newFile = new File("filtered_GREYSCALE_" + this.currentFilename);
        
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wimg, null), "png", newFile);
        } catch (Exception s) {
        }

        initialiseVBox(false);

        image = wimg;
        imgv = new ImageView(wimg);
        vbox.getChildren().add(imgv);
        
    }
    
    private Color[][] getPixelData() {
        PixelReader pr = image.getPixelReader();
        Color[][] pixels = new Color[(int) image.getWidth()][(int) image.getHeight()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                pixels[i][j] = pr.getColor(i, j);
            }
        }

        return pixels;
    }

    private Color[][] getPixelDataExtended() {
        PixelReader pr = image.getPixelReader();
        Color[][] pixels = new Color[(int) image.getWidth() + 2][(int) image.getHeight() + 2];

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels.length; j++) {
                pixels[i][j] = new Color(1.0, 1.0, 1.0, 1.0);
            }
        }

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                pixels[i + 1][j + 1] = pr.getColor(i, j);
            }
        }

        return pixels;
    }

    private void initialiseStage(Stage stage) {
        stage.setTitle("Image Processor");
        scene = new Scene(new VBox(), STAGE_WIDTH, STAGE_HEIGHT);
        scene.setFill(Color.OLDLACE);        
    }

    @Override
    public void start(Stage stage) {
        
        initialiseStage(stage);

        initialiseVBox(true);
        
        createMenuItems();
        
        enableMenuItem("open");
        
        createStage(stage);
    }
    
    private void createStage(Stage stage) {
        
        Menu menuFile = new Menu("File");
        
        MenuItem open = getMenuItem("open");

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image File");
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    enableAllMenuItems();
                    disableMenuItem("open");
                    openFile(file);
                }
            }
        });

        menuFile.getItems().add(open);

        MenuItem close = getMenuItem("close");

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                disableMenuItem("close");
                closeFile();
            }
        });
        
        menuFile.getItems().add(close);

        Menu menuTools = new Menu("Tools");

        MenuItem greyscale = getMenuItem("greyscale");

        greyscale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                greyscale();
            }
        });

        menuTools.getItems().add(greyscale);

        MenuItem blur = getMenuItem("blur");

        blur.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                filterImage("BLUR");
            }
        });

        menuTools.getItems().add(blur);

        MenuItem sharpen = getMenuItem("sharpen");
        
        sharpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                filterImage("SHARPEN");
            }
        });

        menuTools.getItems().add(sharpen);
        
        MenuItem edge = getMenuItem("edge");

        edge.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                filterImage("EDGE");
            }
        });

        menuTools.getItems().add(edge);

        MenuItem sepia = getMenuItem("sepia");

        sepia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                sepia();
            }
        });

        menuTools.getItems().add(sepia);

        MenuItem emboss = getMenuItem("emboss");

        emboss.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                filterImage("EMBOSS");
            }
        });

        menuTools.getItems().add(emboss);

        MenuItem identity = getMenuItem("identity");

        identity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                filterImage("IDENTITY");
            }
        });

        menuTools.getItems().add(identity);
        
        MenuItem reset = getMenuItem("reset");

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                reset();
            }
        });

        menuTools.getItems().add(reset);
        
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(menuFile, menuTools);
        
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox);
        
        stage.setScene(scene);
        
        stage.show();       
    }

    protected void reset() {
        initialiseVBox(false);
        openFile(new File(currentFilename));
    }

    private void initialiseVBox(boolean create) {
        
        final int LEFT = 10;
        final int RIGHT = 10;
        final int TOP = 10;
        final int BOTTOM = 10;
        
        
        if (create) {
            vbox = new VBox();
        }
        vbox.getChildren().clear();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(LEFT,TOP,RIGHT,BOTTOM));        
    }

    private void createMenuItems() {
        menuItems = new ArrayList<MenuItem>();
        menuItems.add(new MenuItem("Open"));
        menuItems.add(new MenuItem("Close"));
        menuItems.add(new MenuItem("Greyscale"));
        menuItems.add(new MenuItem("Blur"));
        menuItems.add(new MenuItem("Sharpen"));
        menuItems.add(new MenuItem("Sepia"));
        menuItems.add(new MenuItem("Emboss"));
        menuItems.add(new MenuItem("Edge"));    
        menuItems.add(new MenuItem("Identity"));   
        menuItems.add(new MenuItem("Reset"));   
        disableAllMenuItems();
    }
    
    private void disableAllMenuItems() {
        for (MenuItem m: menuItems) {
            m.setDisable(true);
        }
    }

    private void enableAllMenuItems() {
        for (MenuItem m: menuItems) {
            m.setDisable(false);
        }
    }
    
    private void disableMenuItem(String item) {
        for (MenuItem m: menuItems) {
            if (m.getText().equalsIgnoreCase(item)) {
                m.setDisable(true);
            }
        }
    }

    private void enableMenuItem(String item) {
        for (MenuItem m: menuItems) {
            if (m.getText().equalsIgnoreCase(item)) {
                m.setDisable(false);
            }
        }
    }

    private MenuItem getMenuItem(String name) {
        for (MenuItem m: menuItems) {
            if (m.getText().equalsIgnoreCase(name)) {
                return m;
            }
        }
        
        return null;
    }

    private void closeFile() {
        enableMenuItem("open");
        initialiseVBox(false);
    }

    private void openFile(File file) {
        
        image = new Image("file:" + file.getPath());
        
        if (image.getWidth() != image.getHeight()) {
            Alert alert = new Alert(AlertType.ERROR, "Image is not square.", ButtonType.OK);
            alert.showAndWait();   
            return;
        }
        
        imgv = new ImageView();
        imgv.setImage(image);
        vbox.getChildren().add(imgv);
        currentFilename = file.getName();
    }

    public static void main(String[] args) {
        launch(args);
    }    
}
