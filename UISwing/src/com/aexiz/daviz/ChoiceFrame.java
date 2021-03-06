package com.aexiz.daviz;

import com.aexiz.daviz.images.ImageRoot;
import com.aexiz.daviz.ui.JCarousel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

class ChoiceFrame extends JDialog {

    private static final long serialVersionUID = -7132485866652653356L;

    TimelineFrame timeline;

    JCarousel carousel;

    ChoiceFrame(Window owner) {
        super(owner, "Choice");
        setAutoRequestFocus(false);
        ArrayList<Image> icons = new ArrayList<Image>();
        icons.add(new ImageIcon(ImageRoot.class.getResource("d16/arrow_branch.png")).getImage());
        icons.add(new ImageIcon(ImageRoot.class.getResource("d32/arrow_branch.png")).getImage());
        setIconImages(icons);

        carousel = new JCarousel();
        carousel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                timeline.timeline.requestFocus();
            }
        });

        JScrollPane scrollPane = new JScrollPane(carousel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        Container contentPane = getContentPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    // Called by Controller to create actions
    void registerActions(Controller controller) {
    }

    // Called  by Controller to refresh registered actions
    void refreshActions(Controller controller) {
    }

    // Called by Controller to change menubar
    void populateMenuBar(Controller controller, JMenuBar menubar) {
    }

}
