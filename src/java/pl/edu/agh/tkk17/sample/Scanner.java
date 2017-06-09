package pl.edu.agh.tkk17.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Scanner implements Iterator<Token>, Iterable<Token>
{
    private InputStream input;
    private int position;
    private char character;
    private boolean end;
    private StringBuilder numBuilder = new StringBuilder();

    public Scanner(InputStream input)
    {
        this.input = input;
        this.position = -1;
        this.end = false;
        this.readChar();
    }

    private void readChar()
    {
        try {
            int character = this.input.read();
            this.position += 1;
            boolean end = character < 0;
            this.end = end;
            if (!end) {
                this.character = (char) character;

            }
        } catch (IOException e) {
            throw new UncheckedIOException("Scanner input exception.", e);
        }
    }

    public boolean hasNext()
    {
        return !this.end;
    }

    public Token next()
    {
        if (this.end) {
            throw new NoSuchElementException("Scanner input ended.");
        }

        while(this.character == ' ')
        {
            this.readChar();
        }

        char character = this.character;
        Token token;

        if (character == '+') {
            token = this.makeToken(TokenType.ADD);
            this.readChar();
        } else if (character == '-') {
            token = this.makeToken(TokenType.SUB);
            this.readChar();
        } else if (character == '*') {
            token = this.makeToken(TokenType.MUL);
            this.readChar();
        } else if (character == '/') {
            token = this.makeToken(TokenType.DIV);
            this.readChar();
        } else if (character == '(') {
            token = this.makeToken(TokenType.LBRA);
            this.readChar();
        } else if (character == ')') {
            token = this.makeToken(TokenType.RBRA);
            this.readChar();
        }
        else if (character >= '0' && character <= '9') {
            while((this.character >= '0' && this.character <= '9') || (this.character == '.')){
                numBuilder.append(this.character);
                this.readChar();
            }
            String value = numBuilder.toString();
            token = this.makeToken(TokenType.NUM, value);
            numBuilder.delete(0,numBuilder.toString().length());
        } else if (character == '\n' || character == '\u0000') {
            token = this.makeToken(TokenType.END);
            this.readChar();
        } else {
            String location = this.locate(this.position);
            throw new UnexpectedCharacterException(character, location);
        }

        return token;

    }

    public Iterator<Token> iterator()
    {
        return this;
    }

    private Token makeToken(TokenType type)
    {
        return new Token(type, this.position);
    }

    private Token makeToken(TokenType type, String value)
    {
        return new Token(type, this.position, value);
    }

    private static String locate(int position)
    {
        return String.valueOf(position);
    }
}
