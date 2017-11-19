package LAB053;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.function.Consumer;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

interface MousePressListener extends MouseListener
{
    @Override
    public default void mouseEntered(MouseEvent e) {}

    @Override
    public default void mouseExited(MouseEvent e) {}

    @Override
    public default void mousePressed(MouseEvent e) {}

    @Override
    public default void mouseReleased(MouseEvent e) {}
}
