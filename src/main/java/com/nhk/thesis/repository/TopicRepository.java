package com.nhk.thesis.repository;

import com.nhk.thesis.entity.Topic;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TopicRepository extends MongoRepository<Topic, String> {

    List<Topic> findAllBySemester(String semester);

    List<Topic> findAllBySemesterAndLecturer(String semester, String lecturer);

    Topic findBySemesterAndMemberContains(String semester, String student);

    List<Topic> findAllByMemberContains(String student);

    List<Topic> findAllByLecturer(String lecturer);

    @Aggregation({"{ $search: { index: 'topicSearch', text: {query: ?0, path: { 'wildcard': '*' }}}}", "{$project: {id: 1, name: 1, enName: 1, semester: 1}}"})
    List<Topic> search(String keyword);

}
