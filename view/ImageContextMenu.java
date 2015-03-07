package view;

import controller.Controller;
import model.Language;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ImageContextMenu extends JPopupMenu
{
    private final JMenuItem rename;
    private final JMenuItem delete;
    private Language language;

    public ImageContextMenu(final Controller controller)
    {
        rename = new JMenuItem();
        rename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String str = JOptionPane.showInputDialog(language.getString("renameImage"));
                if(str == null || str.isEmpty())
                    return;

                controller.imageRenamed(str);
            }
        });
        add(rename);

        delete = new JMenuItem();
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int val = JOptionPane.showConfirmDialog(null,
                        language.getString("deleteImageConfirm"), "",
                        JOptionPane.YES_NO_OPTION);
                if (val == JOptionPane.YES_OPTION)
                    controller.imageDeleted();
            }
        });
        add(delete);
    }

    public void setLanguage(Language lang)
    {
        if(language == null)
            language = lang;
        rename.setText(lang.getString("menuEditRename"));
        delete.setText(lang.getString("menuEditDelete"));
    }
}
