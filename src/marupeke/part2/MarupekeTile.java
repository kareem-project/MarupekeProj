package marupeke.part2;

import javafx.scene.Node;

/**
 * @author ianw, benrhard
 */

public class MarupekeTile {

        private Mark mark = Mark.BLANK;
        private boolean editable = true;



        public Mark getMark() {
            return mark;
        }

        public void setMark(Mark mark) {
            if (isEditable()) {
                this.mark = mark;
            }
        }

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

        @Override
        public String toString() {
            return getMark().toString();
        }

    public enum Mark {
        BLANK("_"), SOLID("#"), CROSS("x"), NOUGHT("o");
        private String representation;

        private Mark(String s) {
            this.representation = s;
        }

        public String toString() {
            return representation;
        }
    }
}
