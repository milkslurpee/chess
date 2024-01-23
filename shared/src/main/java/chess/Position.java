package chess;

import java.util.Objects;

public class Position implements ChessPosition{

    int row;
    int column;

    public Position(Integer row, Integer col) {
        this.row = row;
        this.column = col;
    }
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public int getColumn() {
        return column;
    }
}
