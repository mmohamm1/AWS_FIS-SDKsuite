package com.fis.invoke.fisProject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.fis.AWSFIS;
import com.amazonaws.services.fis.AWSFISAsyncClientBuilder;
import com.amazonaws.services.fis.model.ExperimentAction;
import com.amazonaws.services.fis.model.GetExperimentRequest;
import com.amazonaws.services.fis.model.GetExperimentResult;
import com.amazonaws.services.fis.model.StartExperimentRequest;
import com.amazonaws.services.fis.model.StartExperimentResult;
import com.amazonaws.services.fis.model.StopExperimentRequest;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	
	public static void main(String[] args) throws Exception {

		AWSCredentials credentials = null;

		try {
			credentials = new ProfileCredentialsProvider().getCredentials();

		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (~/.aws/credentials), and is in valid format.", e);
		}

		AWSFIS fisClient = AWSFISAsyncClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Experiment Template ID--> ");
		String temp=sc.nextLine();
		StartExperimentResult fis = fisClient
				.startExperiment(new StartExperimentRequest().withExperimentTemplateId(args[0]));//EXTE7dgqPdxVGj3ur
		Thread.sleep(50000);
		GetExperimentResult getExpResult = fisClient
				.getExperiment(new GetExperimentRequest().withId(fis.getExperiment().getId()));
		System.out.println("Experiment start date and time --> " + getExpResult.getExperiment().getStartTime());
		System.out.println("Experiment current status --> " + getExpResult.getExperiment().getState());
		fisClient.stopExperiment(new StopExperimentRequest().withId(fis.getExperiment().getId()));
		Thread.sleep(20000);
		System.out.println("Experiment start date and time --> "
				+ fisClient.getExperiment(new GetExperimentRequest().withId(fis.getExperiment().getId()))
						.getExperiment().getEndTime());
		System.out.println("Experiment current status --> "
				+ fisClient.getExperiment(new GetExperimentRequest().withId(fis.getExperiment().getId()))
						.getExperiment().getState());
	}
}
