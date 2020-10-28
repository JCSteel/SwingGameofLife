package GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


import javax.swing.JButton;


class board extends JPanel implements ActionListener, MouseListener{


    static int size = 50;  //used mainly for board size
    static int cellSize = 15;
    static boolean bool[][];
    static JButton cells[][];
    static Color c;
    static Timer timer;
    static boolean isStopped;
    static boardDB db;

    public board() {
    	db = new boardDB();
    	c = Color.white;
    	bool = new boolean[size][size];
		addMouseListener(this);
		isStopped = true;	// initialize flag because no game is started yet
		
		// setting all cells in board to dead
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				bool[i][j] = false;
			}
		}
    }
    
    public void startGame() {
    	// save board state as previous board
    	db.saveBoard(bool);
    	
    	// if board is empty, no game to start; do nothing
    	if (boardIsEmpty()) {
    		System.out.println("Cannot start game; there are no live cells."); 
    	}
    	// if simulation is stopped, start simulation
    	else if (isStopped) {
	    	timer = new Timer(100, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	
	                boolean[][] temp = new boolean[size][size];
	
	                for (int i = 0; i < size; i++) {
	                    for (int j = 0; j < size; j++) {
	                        int count = countNeighbors(i, j);
	                        //rules
	                        if (bool[i][j]) {
	                            if (count < 2)
	                                temp[i][j] = false;
	
	                            if (count == 3 || count == 2)
	                                temp[i][j] = true;
	
	                            if (count > 3)
	                                temp[i][j] = false;
	
	                        } else {
	                            if (count == 3)
	                                temp[i][j] = true;
	                        }
	                    }
	                }
	                updateBoard();
	               bool = temp;
	            }
	        });
	    	
	    	timer.start();
	    	isStopped = false;
    	}
    	// otherwise simulation is already running; do nothing
    	else {
    		System.out.println("Simulation is already running. "); 
    	}
    }
    
    private ActionListener ActionListener() {
		// TODO Auto-generated method stub
		return null;
	}

	public void stopGame() {
		// if simulation is running, stop
    	if (!isStopped) {
    		timer.stop();	
    		isStopped = true;
    	}
    	// otherwise there is no simulation to stop
    	else {
    		System.out.println("No simulation to stop."); 
    	}
    }
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == cells[0][0]) {
    		cells[0][0].setBackground(Color.white);
    	}
    }

    static int countNeighbors(int x, int y) {

        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                try {
                    if (bool[i][j]) {
                        count++;
                    }

                } catch (Exception e) {  // catches out of bounds exceptions at ends of board
                //    System.out.println("error");
                    // putting anything in here will constantly run
                }
            }
        }
        if (bool[x][y])
            count--;
        return count;
    }
    
    public void getPreset(int preset) {
    	clearBoard();
    	switch (preset) {
    	case 0:
    		bool = boardDB.getRandomPreset();
    		updateBoard();
    		break;
    	case 1:
    		bool = boardDB.getExploderPreset();
    		updateBoard();
    		break;
    	case 2:
    		bool = boardDB.getGliderPreset();
    		updateBoard();
    		break;
    	}
    	
    }
    
    public void clearBoard() {
    	if (isStopped) {
    		for (int i = 0; i < size; i++) {
        		for (int j = 0; j < size; j++) {
        			bool[i][j] = false;
        		}
        	}
    	}
    	else {
    		stopGame();
    	}
    	updateBoard();
    }
    
    public void loadPreviousBoard() {
    	if (db.getPreviousBoard() != null) {
    		bool = db.getPreviousBoard();
    	}
    	setColor(c);
    }

    public void setColor(Color color) {
    	c = color;
    	updateBoard();
    }
    
    public void mouseClicked (MouseEvent e) {
		//Don't actually need.
	}
	
	public void mousePressed (MouseEvent e) {
		//if (!game.isStarted());
		if (e.getY()/cellSize >= size || e.getX()/cellSize >= size) {
			JOptionPane.showMessageDialog(null, "Error. Click out of bounds.");
		}
		else if (bool[e.getY()/cellSize][e.getX()/cellSize]) {
			bool[e.getY()/cellSize][e.getX()/cellSize] = false;
		}
		else {
			bool[e.getY()/cellSize][e.getX()/cellSize] = true;
		}
		updateBoard();
	}
	
	public void mouseEntered (MouseEvent e) {	
		//Don't actually need.
	}
	
	public void mouseReleased (MouseEvent e) {
		//Don't actually need.
	}
	
	public void mouseExited (MouseEvent e) {	
		//Don't actually need.
	}
	
	public void updateBoard() {
		removeAll();
		revalidate();
		repaint();
	}
	
	//Draws the board.
	public void paint(Graphics g) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (bool[i][j] == true) {
					g.setColor(Color.black);
					g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
				}
				else {
					g.setColor(c);
					g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
				}
				g.setColor(Color.black);
				g.drawRect(j*cellSize, i*cellSize, cellSize, cellSize);
			}
		}
	}

	// checks board to see if any live cells present; returns a boolean. 
	public boolean boardIsEmpty() {
		boolean isEmpty = true; 	// assume the board is empty 
		
		// iterate through the board and check each cell to see if alive
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++ ) {
				// if cell at board position (i,j) is alive, board is not empty
				if (bool[i][j] == true) {
					isEmpty = false;
				}
			}
		}
		
		return isEmpty; 
	}

}