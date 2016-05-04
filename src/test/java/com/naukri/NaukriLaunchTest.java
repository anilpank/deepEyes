package com.naukri;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NaukriLaunchTest {
	
	@Test
	public void test_Write() {
		List<JobAppliedStatus> jobStatuses = new ArrayList<JobAppliedStatus>();
		jobStatuses.add(new JobAppliedStatus(true, "File is processed"));
		new NaukriLaunch().write(jobStatuses);
	}

}
