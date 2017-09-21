import acm.graphics.*;
import java.awt.event.*;

import com.shpp.cs.a.graphics.WindowProgram;

public class RubberBanding extends WindowProgram {
    public void run() {
        addMouseListeners();
    }

    /* The line that is currently being dragged around, or null if no
     * line is currently being drawn.  
     */
    private GLine currentLine;
    public void mousePressed(MouseEvent e) {
        /* Set up a new rubber-banded line by setting the current line
         * to be a new line with both endpoints at the indicated point.  
         */
        currentLine = new GLine(e.getX(), e.getY(), e.getX(), e.getY());
        add(currentLine);
    }

    public void mouseDragged(MouseEvent e) {
        /* There must be a line currently being drawn, since otherwise
         * the mouse couldn't be dragged. Update its endpoint to be
         * the current mouse position.
         */
        currentLine.setEndPoint(e.getX(), e.getY());
    }

    /* There is no need to have a mouseReleased method, since once the
     * mouse is released the dragging stops.  
     */
}