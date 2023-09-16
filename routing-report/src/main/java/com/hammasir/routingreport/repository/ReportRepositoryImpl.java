package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.Report;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ReportRepositoryImpl extends ReportRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Report> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Report> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Report> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Report getOne(Long aLong) {
        return null;
    }

    @Override
    public Report getById(Long aLong) {
        return null;
    }

    @Override
    public Report getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Report> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Report> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Report> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Report> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Report> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Report> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Report, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Report> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Report> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Report> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Report> findAll() {
        return null;
    }

    @Override
    public List<Report> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Report entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Report> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Report> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Report> findAll(Pageable pageable) {
        return null;
    }
}
