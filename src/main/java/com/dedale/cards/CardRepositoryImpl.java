package com.dedale.cards;

import java.util.List;

import javax.inject.Singleton;

import com.dedale.common.InMemoryRepository;
import com.dedale.common.Repository;

@Singleton
public class CardRepositoryImpl implements CardRepository {
    
    private Repository<Card> delegate = new InMemoryRepository<>();

    public Card getById(long id) {
        return delegate.getById(id);
    }

    public void add(Card item) {
        delegate.add(item);
    }

    public void delete(Card item) {
        delegate.delete(item);
    }

    public List<Card> getAll() {
        return delegate.getAll();
    }

}