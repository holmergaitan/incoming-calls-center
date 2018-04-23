package com.callcenter.app.model.call;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Represents a Call entity in the system
 *
 * @author Holmer Gaitan
 * @version 1.0
 */
@Entity
@Table(name = "call", schema = "clc")
public class Call implements Comparable<Call> {

	/** The caller id. */
	@Id
	@SequenceGenerator(name = "id_sequence", sequenceName = "clc" + ".id_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	/** The caller name. */
	@NotNull
	@Column(name = "name")
	private String name;

	/** The message. */
	@NotNull
	@Column(name = "message")
	private String message;

	/** The response provided by the employee. */
	@Column(name = "response")
	private String response;

	/** The state. */
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private CallState state;

	/** The call duration. */
	@NotNull
	@Column(name = "duration")
	private long duration;

	/** Number of attempts before an employee answer */
	@Column(name = "attempts")
	private Integer attempts = 0;

	/** The answer date. */
	@Column(name = "incoming_call_date")
	private Date answerDate;

	/**
	 * Instantiates a new call.
	 */
	public Call() {
	}

	/**
	 * Instantiates a new call.
	 *
	 * @param name
	 *            the name
	 * @param message
	 *            the message
	 */
	public Call(final String name, final String message) {

		this.message = message;
		this.name = name;
		state = CallState.PENDING;
		duration = getRandomCallDuration();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {

		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(final Integer id) {

		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(final String name) {

		this.name = name;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {

		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(final String message) {

		this.message = message;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getResponse() {

		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */
	public void setResponse(String response) {

		this.response = response;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public CallState getState() {

		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(CallState state) {

		this.state = state;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public long getDuration() {

		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration
	 *            the new duration
	 */
	public void setDuration(long duration) {

		this.duration = duration;
	}

	/**
	 * Gets the random call duration.
	 *
	 * @return the random call duration
	 */
	private long getRandomCallDuration() {

		return 5 + (long) (Math.random() * ((10 - 5) + 1));
	}

	/**
	 * Gets the attempts.
	 *
	 * @return the attempts
	 */
	public Integer getAttempts() {

		return attempts;
	}

	/**
	 * Sets the attempts.
	 *
	 * @param attempts
	 *            the new attempts
	 */
	public void setAttempts(final Integer attempts) {

		this.attempts = attempts;
	}

	/**
	 * Gets the answer date.
	 *
	 * @return the answer date
	 */
	public Date getAnswerDate() {

		return answerDate;
	}

	/**
	 * Sets the answer date.
	 *
	 * @param answerDate
	 *            the new answer date
	 */
	public void setAnswerDate(final Date answerDate) {

		this.answerDate = answerDate;
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public int compareTo(Call arg0) {

		return 0;
	}
}
