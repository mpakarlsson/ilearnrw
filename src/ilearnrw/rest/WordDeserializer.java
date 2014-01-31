package ilearnrw.rest;

import ilearnrw.textclassification.Word;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

class WordDeserializer implements JsonDeserializer<Word>
{
    public Word deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
        return new Word(json.getAsString());
    }
}