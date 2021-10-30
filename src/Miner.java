import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class Miner extends JFrame {
    private Game game;
    private JLabel label;
    private JPanel panel;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args)
    {
        new Miner();
    }

    private Miner() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImage();
        initLable();
        initFrame();
        initPanel();
    }
    private void initLable(){
        label = new JLabel("Welcome");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                for(Coord coord: Ranges.getAllCoords()) {

                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/IMAGE_SIZE;
                int y = e.getY()/IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton() == MouseEvent.BUTTON1){
                    game.pressLeftButton(coord);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(coord);
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                label.setText(getMassege());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private String getMassege() {
        switch (game.getState()){
            case PLAYED: return  "Think twice";
            case BOMBED: return "YOU LOSER! HA-HA-HA!";
            case WINNER: return "Congratulations!";
            default: return "Welcome";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Miner");
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        setIconImage(getIMage("icon"));
        pack();
        setSize(Ranges.getSize().x * IMAGE_SIZE + 15, Ranges.getSize().y * IMAGE_SIZE + 40);
    }
    private void setImage(){
        for (Box box: Box.values()){
            box.image = getIMage(box.name());
        }
    }

    private Image getIMage(String name) {
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
