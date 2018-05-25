package ro.micsa.scores.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import ro.micsa.scores.domain.Score;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

class ScoresRepositoryImpl implements ScoresRepositoryCustom {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public Flux<Score> find(ScoresFilter filter) {
        Query query = new Query();
        if (filter != null) {
            List<Criteria> andCriteriaList = new ArrayList<>();

            filter.getTeam().ifPresent(team -> {
                andCriteriaList.add(new Criteria().orOperator(where("team1").is(team), where("team2").is(team)));
            });

            filter.getFrom().ifPresent(from -> andCriteriaList.add(where("date").gte(from)));
            filter.getUntil().ifPresent(until -> andCriteriaList.add(where("date").lte(until)));

            Criteria criteria = new Criteria();
            if(!andCriteriaList.isEmpty()) {
                criteria.andOperator(andCriteriaList.toArray(new Criteria[0]));
            }

            query.addCriteria(criteria);
        }

        query.with(new Sort(Sort.Direction.DESC, "date"));

        return mongoTemplate.find(query, Score.class);
    }
}
