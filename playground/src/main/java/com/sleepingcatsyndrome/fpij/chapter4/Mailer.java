package com.sleepingcatsyndrome.fpij.chapter4;

public class Mailer {
	private String fromAddress;
	private String toAddress;
	private String subject;
	private String body;

	public Mailer from(final String address) {
		fromAddress = address;
		return this;
	}
	public Mailer to(final String address) {
		toAddress = address;
		return this;
	}
	public Mailer subject(final String theSubject) {
		subject = theSubject;
		return this;
	}
	public Mailer body(final String theBody) {
		body = theBody;
		return this;
	}
	public void send() {
		System.out.println(String.format("sending...\n\tFrom: %s\n\tTo: %s\n\tSubject: %s\n\tBody: %s\n", fromAddress, toAddress, subject, body));
	}
}
