package com.callcenter.app.jpa;

import com.callcenter.app.model.call.Call;
import com.callcenter.app.model.call.CallState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Contains CRUD operations related to {@link Call} entity like save, delete,
 * update.
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
public interface ICallDao extends CrudRepository<Call, Integer> {

	/**
	 * Gets the calls by specific state.
	 *
	 * @param state
	 *            the current state that will be used to find calls
	 * @return a call list selected by state
	 */
	@Query("select c from Call c where c.state = :state order by c.answerDate desc ")
	public List<Call> getCallsByState(@Param("state") final CallState state);
}
