package edu.vtac.roveBaseProject.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BuildInfo {

	@Value("${git.branch}")
	private String gitBranch;

	@Value("${git.build.host}")
	private String buildMachine;

	@Value("${git.commit.id.abbrev}")
	private String commitId;

	@Value("${git.commit.time}")
	private String commitTime;

	@Value("${git.commit.user.name}")
	private String lastCommitUser;

	@Value("${build.time}")
	private String buildTime;

	public String getGitBranch() {
		return gitBranch;
	}

	public void setGitBranch(String gitBranch) {
		this.gitBranch = gitBranch;
	}

	public String getBuildMachine() {
		return buildMachine;
	}

	public void setBuildMachine(String buildMachine) {
		this.buildMachine = buildMachine;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

	public String getLastCommitUser() {
		return lastCommitUser;
	}

	public void setLastCommitUser(String lastCommitUser) {
		this.lastCommitUser = lastCommitUser;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	
	
}
