package model;

public enum Direction {
    NORTH {
        @Override
        public int getX(int x, int max) {
            return x;
        }

        @Override
        public int getY(int y, int max) {
            if (y - 1 < 0)
                return max - 1;
            else
                return y - 1;
        }
    },

    EAST {
        @Override
        public int getX(int x, int max) {
            if (x + 1 > max - 1)
                return 0;
            else
                return x + 1;
        }

        @Override
        public int getY(int y, int max) {
            return y;
        }
    },

    SOUTH {
        @Override
        public int getX(int x, int max) {
            return x;
        }

        @Override
        public int getY(int y, int max) {
            if (y + 1 > max - 1)
                return 0;
            else
                return y + 1;
        }
    },

    WEST {
        @Override
        public int getX(int x, int max) {
            if (x - 1 < 0)
                return max - 1;
            else
                return x - 1;
        }

        @Override
        public int getY(int y, int max) {
            return y;
        }
    };

    public abstract int getX(int x, int max);
    public abstract int getY(int y, int max);
}
