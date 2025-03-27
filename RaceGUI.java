import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * FINISHED ON 3/27/2025
 * Car Racing Game GUI
 * Manages game screens, timer, and user interaction.
 * Author: Anna Zadorenko
 * Editor: Gabriel Luciano
 */

public class RaceGUI {
    private final JFrame frame;
    private JPanel welcomePanel, gamePanel;
    private JLabel gameTitle, chooseColorLabel, chooseEngineLabel;
    private JButton startGameButton, startRaceButton, restartButton;
    private CarComponent[] cars = new CarComponent[4];
    private Timer raceTimer;
    private double[] carX = {1144, 1170, 394, 340};
    private double[] carY = {490, 261, 200, 460};
    private JLabel raceTimeLabel;
    private long startTime;
    private JComboBox<String>[] colorPickers = new JComboBox[4];
    private Track track;
    private ArrayList<Stop> stops;
    private ArrayList<Car> listOfCars;
    private RaceLogic logicHandler;
    private ArrayList<Engine> engines;
    private ArrayList<Tire> tires;
    private final Random random;
    private boolean[] carFinished = new boolean[4];
    private double[] finishTimes = new double[4];

    /**
     * Initializes the main game window, sets up the welcome and game screens,
     * and adds them to a CardLayout for easy screen switching.
     */

    public RaceGUI() {
        random = new Random();
        logicHandler = new RaceLogic();
        engines = new ArrayList<>();
        tires = new ArrayList<>();
        track = new Track("Race Track 1");
        listOfCars = new ArrayList<>();
        frame = new JFrame("Car Racing Game");

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());

        setupWelcomeScreen();
        setupGameScreen();

        frame.add(welcomePanel, "Welcome");
        frame.add(gamePanel, "Game");

        frame.setVisible(true);
    }

    // Car image file paths used when rendering the player's selected car
    private String[] carImagePaths = {
            "/images/Blue.png",
            "/images/Red.png",
            "/images/Yellow.png",
            "/images/Green.png",
            "/images/Pink.png"
    };

    // Available color options for car selection in the welcome screen
    private String[] colors = {"Blue", "Red", "Yellow", "Green", "Pink"};  // Match image names

    /**
     * Sets up the welcome screen UI where players select their car colors
     * and start the game. Includes a title label, four car selection dropdowns,
     * and a button to begin the race.
     */
    private void setupWelcomeScreen() {
        welcomePanel = new JPanel(null);
        welcomePanel.setBackground(Color.WHITE);

        gameTitle = new JLabel("Car Racing Game", SwingConstants.CENTER);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 28));
        gameTitle.setBounds(630, 50, 300, 40);
        welcomePanel.add(gameTitle);

        colorPickers = new JComboBox[4];

        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel("Choose Car " + (i + 1) + ":");
            label.setBounds(590, 300 + i * 50, 200, 30);
            welcomePanel.add(label);

            colorPickers[i] = new JComboBox<>(colors); // Uses correct color names
            colorPickers[i].setBounds(850, 300 + i * 50, 100, 30);
            welcomePanel.add(colorPickers[i]);
        }

        startGameButton = new JButton("Start the Game");
        startGameButton.setBounds(700, 600, 150, 50);
        startGameButton.addActionListener(e -> showGameScreen());
        welcomePanel.add(startGameButton);
    }

    /**
     * Sets up the game screen where the race takes place.
     * Initializes the game panel with a custom track drawing using overridden paintComponent().
     * This includes the outer track, inner border, gray road, dashed lines, checkered finish lines,
     * and red-white striped borders for decoration and clarity.
     */
    private void setupGameScreen() {
        gamePanel = new JPanel(null);
        gamePanel.setBackground(Color.WHITE);

        gamePanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int trackX = 335, trackY = 200, trackWidth = 900, trackHeight = 350;
                int borderThickness = 10; // Thickness of the red-white border
                int laneWidth = 150; // Width of the racing lane

                // Draw Green Borders
                g2d.setColor(Color.GREEN);
                g2d.fillRect(trackX - 30, trackY - 30, trackWidth + 60, trackHeight + 60);

                // Draw Outer Black Track Lines
                g2d.setColor(Color.BLACK);
                g2d.drawRect(trackX, trackY, trackWidth, trackHeight);

                // Draw Inner Black Track Lines (Empty Center)
                g2d.drawRect(trackX + laneWidth, trackY + laneWidth, trackWidth - 2 * laneWidth, trackHeight - 2 * laneWidth);

                // Draw Gray Road Between the Two Black Lines
                g2d.setColor(Color.GRAY);
                g2d.fillRect(trackX, trackY, trackWidth, laneWidth); // Top lane
                g2d.fillRect(trackX, trackY + trackHeight - laneWidth, trackWidth, laneWidth); // Bottom lane
                g2d.fillRect(trackX, trackY, laneWidth, trackHeight); // Left lane
                g2d.fillRect(trackX + trackWidth - laneWidth, trackY, laneWidth, trackHeight); // Right lane

                // Draw Light Gray Dashed Center Line
                drawDashedCenterline(g2d, trackX, trackY, trackWidth, trackHeight, laneWidth);

                //Finish lin
                drawCheckeredGrid(g2d, 335, 395);
                drawCheckeredGrid(g2d, 1095, 340);

                //Upper finish line
                drawFinishGrid(g2d, 1000, 405);
                drawFinishGrid(g2d, 530, 200);

                // Draw Red and White Striped Borders
                drawStripedBorder(g2d, trackX - borderThickness, trackY - borderThickness, trackWidth + 2 * borderThickness, trackHeight + 2 * borderThickness);
                drawStripedBorder(g2d, trackX + laneWidth - borderThickness, trackY + laneWidth - borderThickness, trackWidth - 2 * laneWidth + 2 * borderThickness, trackHeight - 2 * laneWidth + 2 * borderThickness);

                // Area inside the track
                g2d.setColor(Color.WHITE);
                g2d.fillRect(trackX + laneWidth, trackY + laneWidth, trackWidth - 2 * laneWidth, trackHeight - 2 * laneWidth);

            }

            /**
             * Draws a vertical checkered finish line using alternating black and white squares.
             * The grid has more rows than columns, creating a tall finish line.
             *
             * @param g2d The graphics context to draw with.
             * @param x   The starting x-coordinate.
             * @param y   The starting y-coordinate.
             */
            private void drawFinishGrid(Graphics2D g2d, int x, int y) {
                int squareSize = 5;
                int rows = 29;
                int cols = 4;

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        if ((row + col) % 2 == 0) {
                            g2d.setColor(Color.BLACK);
                        } else {
                            g2d.setColor(Color.WHITE);
                        }
                        g2d.fillRect(x + (col * squareSize), y + (row * squareSize), squareSize, squareSize);
                    }
                }
            }


            /**
             * Draws a horizontal checkered finish line using alternating black and white squares.
             * The grid has more columns than rows, creating a wide finish strip.
             *
             * @param g2d The graphics context to draw with.
             * @param x   The starting x-coordinate.
             * @param y   The starting y-coordinate.
             */
            private void drawCheckeredGrid(Graphics2D g2d, int x, int y) {
                int squareSize = 5;
                int rows = 4;
                int cols = 29;

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        if ((row + col) % 2 == 0) {
                            g2d.setColor(Color.BLACK);
                        } else {
                            g2d.setColor(Color.WHITE);
                        }
                        g2d.fillRect(x + (col * squareSize), y + (row * squareSize), squareSize, squareSize);
                    }
                }
            }


            /**
             * Draws light gray dashed lines at the center of each racing lane
             * (top, bottom, left, and right) to visually divide the track lanes.
             *
             * @param g2d        The Graphics2D context used for drawing.
             * @param x          The x-coordinate of the top-left corner of the track.
             * @param y          The y-coordinate of the top-left corner of the track.
             * @param width      The total width of the track.
             * @param height     The total height of the track.
             * @param laneWidth  The width of a single racing lane, used to determine centerline positions.
             */
            private void drawDashedCenterline(Graphics2D g2d, int x, int y, int width, int height, int laneWidth) {
                float[] dashPattern = {15, 10}; // Dash length, space length
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
                g2d.setColor(Color.LIGHT_GRAY);

                // Draw dashed centerlines
                g2d.drawLine(x + laneWidth / 2, y, x + laneWidth / 2, y + height); // Left vertical centerline
                g2d.drawLine(x + width - laneWidth / 2, y, x + width - laneWidth / 2, y + height); // Right vertical centerline
                g2d.drawLine(x, y + laneWidth / 2, x + width, y + laneWidth / 2); // Top horizontal centerline
                g2d.drawLine(x, y + height - laneWidth / 2, x + width, y + height - laneWidth / 2); // Bottom horizontal centerline
            }

            /**
             * Draws a red and white striped border around the track area to enhance
             * visual boundaries. The border appears along the top, bottom, left, and right edges.
             *
             * @param g2d     The Graphics2D object used for drawing.
             * @param x       The x-coordinate of the top-left corner of the border area.
             * @param y       The y-coordinate of the top-left corner of the border area.
             * @param width   The total width of the area to be bordered.
             * @param height  The total height of the area to be bordered.
             */
            private void drawStripedBorder(Graphics2D g2d, int x, int y, int width, int height) {
                int stripeSize = 15;
                boolean isRed = true;
                for (int i = x; i < x + width; i += stripeSize) {
                    g2d.setColor(isRed ? Color.RED : Color.WHITE);
                    g2d.fillRect(i, y, stripeSize, 7); // Top border
                    g2d.fillRect(i, y + height - 7, stripeSize, 7); // Bottom border
                    isRed = !isRed;
                }
                for (int j = y; j < y + height; j += stripeSize) {
                    g2d.setColor(isRed ? Color.RED : Color.WHITE);
                    g2d.fillRect(x, j, 7, stripeSize); // Left border
                    g2d.fillRect(x + width - 7, j, 7, stripeSize); // Right border
                    isRed = !isRed;
                }
            }

        };

        gamePanel.setBackground(Color.WHITE);

        //Add Race Timer Label
        raceTimeLabel = new JLabel("Race Time: 0.0s");
        raceTimeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        raceTimeLabel.setBounds(1200, 25, 200, 30); // Position at top-right
        gamePanel.add(raceTimeLabel);

        //Add Game Title label
        JLabel gameTitle = new JLabel("Car Racing Game", SwingConstants.CENTER);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 28));
        gameTitle.setForeground(Color.BLACK);
        gameTitle.setBounds(620, 30, 300, 40);
        gamePanel.add(gameTitle);

        //A point
        JLabel ALabel = new JLabel("A");
        ALabel.setBounds(1156, 424, 30, 100);
        track.addStop(new Stop(new Position(1156, 424)));
        ALabel.setForeground(Color.WHITE);
        gamePanel.add(ALabel);

        //B point
        JLabel BLabel = new JLabel("B");
        BLabel.setBounds(1156, 224, 30, 100);
        track.addStop(new Stop(new Position(1156, 224)));
        BLabel.setForeground(Color.WHITE);
        gamePanel.add(BLabel);

        //C point
        JLabel CLabel = new JLabel("C");
        CLabel.setBounds(406, 224, 30, 100);
        track.addStop(new Stop(new Position(406, 224)));
        CLabel.setForeground(Color.WHITE);
        gamePanel.add(CLabel);

        //D point
        JLabel DLabel = new JLabel("D");
        DLabel.setBounds(406, 424, 30, 100);
        track.addStop(new Stop(new Position(406, 424)));
        DLabel.setForeground(Color.WHITE);
        gamePanel.add(DLabel);

        //Sets the "nextStop" Attributes in the Stop objects
        for (int i = 0; i < track.getStops().size(); i++) {
            if (i == track.getStops().size() - 1) {
                track.getStops().get(i).setNextStop(track.getStops().getFirst());
            }
            else {
                track.getStops().get(i).setNextStop(track.getStops().get(i + 1));
            }
        }

        //adds the aforementioned Stops to the ArrayList of stops.
        stops = track.getStops();

        //Creating Engines
        Engine V4engine = new Engine(50);
        Engine V6Engine = new Engine(75);
        Engine V8Engine = new Engine(100);
        Engine V10Engine = new Engine(125);

        //storing engines
        engines.add(V4engine);
        engines.add(V6Engine);
        engines.add(V8Engine);
        engines.add(V10Engine);

        //Creating Tires
        Tire ToyoTire = new Tire(25);
        Tire FirestoneTire = new Tire(50);
        Tire PirelliTire = new Tire(75);
        Tire GoodyearTire = new Tire(100);

        //storing tires
        tires.add(ToyoTire);
        tires.add(FirestoneTire);
        tires.add(PirelliTire);
        tires.add(GoodyearTire);

        //Making and Storing Cars
        listOfCars.add(new Car(randomObject(engines), randomObject(tires), stops.get(0), track));
        listOfCars.add(new Car(randomObject(engines), randomObject(tires), stops.get(1), track));
        listOfCars.add(new Car(randomObject(engines), randomObject(tires), stops.get(2), track));
        listOfCars.add(new Car(randomObject(engines), randomObject(tires), stops.get(3), track));

        //hands the track to the logicHandler
        logicHandler.setTrack(track);

        //hands the list of stops and cars to the logicHandler
        logicHandler.setListOfStops(stops);
        logicHandler.setListOfCars(listOfCars);

        //Creates the "Start the Race" button
        startRaceButton = new JButton("Start the Race");
        startRaceButton.setBounds(600, 700, 150, 50);
        startRaceButton.addActionListener(e -> {startRace();});
        gamePanel.add(startRaceButton);

        //Creates the "Start Again" button
        restartButton = new JButton("Start Again");
        restartButton.setBounds(800, 700, 150, 50);
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        gamePanel.add(restartButton);

    }

    /**
     * Displays the game screen by switching from the welcome panel to the main game panel.
     * Resets race UI elements and initializes car components at their starting positions with correct orientations.
     * This method is triggered when the user presses "Start the Game" on the welcome screen.
     */
    private void showGameScreen() {
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Game");

        //Reset timer and buttons
        raceTimeLabel.setText("Race Time: 0.0s");
        startRaceButton.setVisible(true);
        startRaceButton.setEnabled(true);
        restartButton.setVisible(false);

        //Remove previous cars from the gamePanel
        for (CarComponent car : cars) {
            if (car != null) {
                gamePanel.remove(car);
            }
        }

        //Reset car positions
        carX = new double[]{listOfCars.get(0).getCarPos().getX(),
                            listOfCars.get(1).getCarPos().getX(),
                            listOfCars.get(2).getCarPos().getX(),
                            listOfCars.get(3).getCarPos().getX()};

        carY = new double[]{listOfCars.get(0).getCarPos().getY(),
                            listOfCars.get(1).getCarPos().getY(),
                            listOfCars.get(2).getCarPos().getY(),
                            listOfCars.get(3).getCarPos().getY()};

        //Create new car components and rotate them
        for (int i = 0; i < 4; i++) {
            int selectedIndex = colorPickers[i].getSelectedIndex();
            cars[i] = new CarComponent(carImagePaths[selectedIndex], carX[i], carY[i]);

            //Set car direction (rotation)
            switch (i) {
                case 0:
                    cars[i].setRotationAngle(Math.toRadians(-90));
                    break;  // facing left
                case 1:
                    cars[i].setRotationAngle(Math.toRadians(180));
                    break;   // facing down
                case 2:
                    cars[i].setRotationAngle(Math.toRadians(90));
                    break;    // facing right
                case 3:
                    cars[i].setRotationAngle(Math.toRadians(0));
                    break;  // facing up
            }

            gamePanel.add(cars[i]);
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }
    //This function is called by the action listener on line 380
    private void startRace() {
        listOfCars = logicHandler.getListOfCars();
        startRaceButton.setEnabled(false);
        Random random = new Random();

        //Creates and initializes and arrayList holding all the speed values for the cars
        ArrayList<Double> speeds = new ArrayList<>();
        for (int i = 0; i < listOfCars.size(); i++) {
            speeds.add(listOfCars.get(i).getSpeed());
        }

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            carFinished[i] = false;
            finishTimes[i] = 0;
        }

        /* The timer tells the program when to move the cars and when to turn the cars
         * The 40 in the first parameter of the Timer declaration is the delay between commands (i.e move -> *wait 40 milliseconds* -> move again)
         */
        raceTimer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < listOfCars.size(); i++) {
                    if (carFinished[i]) continue;
                    Car car = listOfCars.get(i);
                    long elapsedMillis = System.currentTimeMillis() - startTime;
                    double elapsedSeconds = elapsedMillis / 1000.0;
                    raceTimeLabel.setText(String.format("Race Time: %.1fs", elapsedSeconds));


                    //Save old position
                    double prevX = car.getCarPos().getX();
                    double prevY = car.getCarPos().getY();

                    //Move car
                    car.move();

                    //Get new position
                    double newX = car.getCarPos().getX();
                    double newY = car.getCarPos().getY();

                    //Calculate direction change
                    double dx = newX - prevX;
                    double dy = newY - prevY;

                    //Compute angle of movement
                    double angle = Math.atan2(dy, dx);
                    double degrees = Math.toDegrees(angle);

                    //Snap to nearest 90 degrees (e.g., 0°, 90°, 180°, 270°)
                    double snapped = Math.round(degrees / 90.0) * 90.0;
                    double snappedRadians = Math.toRadians(snapped);

                    //Update visual
                    cars[i].setLocationDouble(newX, newY);
                    cars[i].setRotationAngle(snappedRadians);


                    if (car.isWinner() && !carFinished[i]) {
                        carFinished[i] = true;
                        finishTimes[i] = (System.currentTimeMillis() - startTime) / 1000.0;
                    }
                }

                gamePanel.repaint();

                boolean allFinished = true;
                for (boolean finished : carFinished) {
                    if (!finished) {
                        allFinished = false;
                        break;
                    }
                }

                if (allFinished) {
                    raceTimer.stop();
                    restartButton.setVisible(true);

                    int winnerIndex = 0;
                    for (int i = 1; i < finishTimes.length; i++) {
                        if (finishTimes[i] < finishTimes[winnerIndex]) {
                            winnerIndex = i;
                        }
                    }

                    StringBuilder results = new StringBuilder();
                    results.append("🏁 Race Over!\n");
                    results.append("Car ").append(winnerIndex + 1)
                            .append(" wins! Finished in ")
                            .append(String.format("%.2f", finishTimes[winnerIndex]))
                            .append(" seconds.\n");

                    for (int i = 0; i < finishTimes.length; i++) {
                        if (i != winnerIndex) {
                            results.append("Car ").append(i + 1)
                                    .append(" finished in ")
                                    .append(String.format("%.2f", finishTimes[i]))
                                    .append(" seconds.\n");
                        }
                    }

                    JOptionPane.showMessageDialog(frame, results.toString());
                }
            }
        });
        raceTimer.start();

    }

    /**
     * Resets the game to its initial state and switches back to the welcome screen.
     * This includes clearing old car components, restoring their starting positions,
     * and re-enabling the race start button for a new round.
     */
    private void restartGame() {
        // Show the welcome screen
        ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "Welcome");

        // Remove previous car components from the game panel
        for (CarComponent car : cars) {
            gamePanel.remove(car);
            cars = new CarComponent[4]; // Reset the array

        }

        // Reset carFinished flags and finish times
        for (int i = 0; i < 4; i++) {
            carFinished[i] = false;
            finishTimes[i] = 0;
        }

        // Create fresh Car objects with new random engine/tire
        listOfCars.clear();
        listOfCars.add(new Car(randomObject(engines), randomObject(tires), stops.get(0), track));
        listOfCars.add(new Car(randomObject(engines), randomObject(tires), stops.get(1), track));
        listOfCars.add(new Car(randomObject(engines), randomObject(tires), stops.get(2), track));
        listOfCars.add(new Car(randomObject(engines), randomObject(tires), stops.get(3), track));

        logicHandler.setListOfCars(listOfCars); // Let logic handler know about new cars

        // Reset car positions
        carX = new double[]{
                listOfCars.get(0).getCarPos().getX(),
                listOfCars.get(1).getCarPos().getX(),
                listOfCars.get(2).getCarPos().getX(),
                listOfCars.get(3).getCarPos().getX()
        };

        carY = new double[]{
                listOfCars.get(0).getCarPos().getY(),
                listOfCars.get(1).getCarPos().getY(),
                listOfCars.get(2).getCarPos().getY(),
                listOfCars.get(3).getCarPos().getY()
        };

        // Recreate CarComponent visuals
        for (int i = 0; i < 4; i++) {
            int selectedIndex = colorPickers[i].getSelectedIndex();
            cars[i] = new CarComponent(carImagePaths[selectedIndex], carX[i], carY[i]);

            switch (i) {
                case 0 -> cars[i].setRotationAngle(Math.toRadians(-90));
                case 1 -> cars[i].setRotationAngle(Math.toRadians(180));
                case 2 -> cars[i].setRotationAngle(Math.toRadians(90));
                case 3 -> cars[i].setRotationAngle(Math.toRadians(0));
            }

            gamePanel.add(cars[i]);
        }

        gamePanel.repaint();
        restartButton.setVisible(false);
        startRaceButton.setEnabled(true);
    }


    /**
     * CarComponent is a custom Swing component used to display and rotate car images on the race track.
     * Each CarComponent holds an image, rotation angle, and position on the panel.
     */
    class CarComponent extends JPanel {
        private BufferedImage carImage;
        private double rotationAngle = 0;
        private Point2D.Double position;

        public void setLocationDouble(double x, double y) {
            this.position = new Point2D.Double(x, y);
            super.setLocation((int) x, (int) y);  // Rounds to int for AWT rendering
        }

        /**
         * Constructs a CarComponent with a given image path and initial position.
         *
         * @param imagePath   the path to the car image resource
         * @param xPosition   the X coordinate of the car's initial position
         * @param yPosition   the Y coordinate of the car's initial position
         */
        public CarComponent(String imagePath, double xPosition, double yPosition) {
            carImage = loadImage(imagePath);
            setOpaque(false);
            setLocationDouble(xPosition, yPosition);

            if (carImage != null) {
                setSize(carImage.getWidth(), carImage.getHeight());
            } else {
                setSize(60, 30); // fallback size if image fails
            }
        }

        /**
         * Loads a car image from the given resource path.
         *
         * @param path the resource path of the image
         * @return a BufferedImage of the car, or null if loading fails
         */
        private BufferedImage loadImage(String path) {
            try {
                return ImageIO.read(getClass().getResource(path));
            } catch (Exception e) {
                System.err.println("Error loading image: " + path);
                return null;
            }
        }

        /**
         * Sets the rotation angle of the car in radians and adjusts the component size
         * based on the new orientation (horizontal vs vertical).
         *
         * @param angleRadians the new rotation angle in radians
         */
        public void setRotationAngle(double angleRadians) {
            this.rotationAngle = angleRadians;

            if (carImage != null) {
                if (Math.abs(Math.toDegrees(rotationAngle)) % 180 == 90) {
                    setSize(carImage.getHeight(), carImage.getWidth()); // 30x60
                } else {
                    setSize(carImage.getWidth(), carImage.getHeight()); // 60x30
                }
            }

            repaint();
        }

        /**
         * Paints the rotated car image using AffineTransform for smooth rotation.
         *
         * @param g the Graphics object used for drawing
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (carImage == null) return;

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int imgW = carImage.getWidth();
            int imgH = carImage.getHeight();
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            AffineTransform transform = new AffineTransform();
            transform.translate(centerX, centerY);
            transform.rotate(rotationAngle);
            transform.translate(-imgW / 2.0, -imgH / 2.0);

            g2d.drawImage(carImage, transform, null);
            g2d.dispose();
        }

    }

    //Simple function that will take in an arrayList of any type and return a random index of said arrayList.
    private <E> E randomObject (ArrayList<E> listOfObjects) {
        return listOfObjects.get(random.nextInt(4));
    }
}