package pl.edu.agh.tkk17.sample;

public enum TokenType
{
    END("END"),
    NUM("NUM"),
    ADD("ADD"),
    SUB("SUB"),
    DIV("DIV"),
    MUL("MUL"),
    LBRA("LBRA"),
    RBRA("RBRA)");

    private final String name;

    TokenType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
