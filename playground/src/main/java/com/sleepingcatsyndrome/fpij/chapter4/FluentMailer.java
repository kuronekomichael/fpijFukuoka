package com.sleepingcatsyndrome.fpij.chapter4;

import java.util.function.Consumer;

public class FluentMailer {
	private String fromAddress;
	private String toAddress;
	private String subject;
	private String body;

	public FluentMailer from(final String address) {
		fromAddress = address;
		return this;
	}
	public FluentMailer to(final String address) {
		toAddress = address;
		return this;
	}
	public FluentMailer subject(final String theSubject) {
		subject = theSubject;
		return this;
	}
	public FluentMailer body(final String theBody) {
		body = theBody;
		return this;
	}
	private void send() {
		System.out.println(String.format("sending...\n\tFrom: %s\n\tTo: %s\n\tSubject: %s\n\tBody: %s\n", fromAddress, toAddress, subject, body));
	}
	public static void send(final Consumer<FluentMailer> block) {
		final FluentMailer mailer = new FluentMailer();
		block.accept(mailer);
		mailer.send();
	}
}
