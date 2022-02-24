import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

class Contributor {
    private String name;
    private Map<String, Integer> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }

    public Contributor() {
        this.skills = new LinkedHashMap<>();
    }

    public Contributor(Contributor contributor) {
        this.name = contributor.getName();
        this.skills = new LinkedHashMap<>();
        this.skills.putAll(contributor.getSkills());
    }

    public void takeInput(Scanner scanner) {
        this.name = scanner.next();
        int skillsCount = scanner.nextInt();
        this.skills = new LinkedHashMap<>(skillsCount);
        for(int i =0; i<skillsCount; i++) {
            String skillName = scanner.next();
            int level = scanner.nextInt();
            this.skills.put(skillName, level);
        }
    }

    public boolean hasSkill(String skill) {
        return this.skills.containsKey(skill);
    }

    @Override
    public String toString() {
        return "Contributor{" +
                "name='" + name + '\'' +
                ", skills=" + skills +
                '}';
    }

    public void upgradeSkill(String skill, int levelRequired) {
        if(levelRequired > this.skills.get(skill)) {
            this.skills.put(skill, this.skills.get(skill) + 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contributor that = (Contributor) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class Role {
    private int level;
    private Set<Contributor> contributors;

    public Role(int level) {
        this.level = level;
        this.contributors = new LinkedHashSet<>();
    }

    public String getContributorsName() {
        List<String> names = this.getContributors().stream().map(Contributor::getName).collect(Collectors.toList());
        StringBuilder nameAsString = new StringBuilder();
        for(String name : names) {
            nameAsString.append(" ").append(name);
        }
        return nameAsString.toString().trim();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Set<Contributor> getContributors() {
        return contributors;
    }

    public void addContributor(Contributor contributors) {
        this.contributors.add(contributors);
    }

    @Override
    public String toString() {
        return "Role{" +
                "level=" + level +
                ", contributors=" + contributors +
                '}';
    }
}

class Project implements Comparable<Project> {
    private String name;
    private long duration;
    private int score;
    private int bestBeforeDays;
    private Map<String, Role> roles;
    private boolean isCompleted;
    private long delta;

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBestBeforeDays() {
        return bestBeforeDays;
    }

    public void setBestBeforeDays(int bestBeforeDays) {
        this.bestBeforeDays = bestBeforeDays;
    }

    public Map<String, Role> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Role> roles) {
        this.roles = roles;
    }

    public void takeInput(Scanner scanner) {
        this.name = scanner.next();
        this.duration = scanner.nextInt();
        this.score = scanner.nextInt();
        this.bestBeforeDays = scanner.nextInt();
        this.delta = this.bestBeforeDays - this.duration;
        int rolesCount = scanner.nextInt();
        this.roles = new LinkedHashMap<>(rolesCount);
        for(int i =0; i<rolesCount; i++) {
            String roleName = scanner.next();
            int level = scanner.nextInt();
            this.roles.put(roleName, new Role(level));
        }
    }

    private boolean hasMentor(String skillName, int juniorSkillLevel) {
        return this.roles.entrySet().stream().anyMatch(entrySet -> !entrySet.getKey().equalsIgnoreCase(skillName) && entrySet.getValue().getContributors().stream().anyMatch(contributor -> contributor.getSkills().get(skillName) > juniorSkillLevel));
    }

    public String getInfoForOutput() {
        StringBuilder output = new StringBuilder(this.name);
        List<String> contributorsList = this.getRoles().values().stream().map(Role::getContributorsName).collect(Collectors.toList());
        for(String names : contributorsList) {
            output.append("\n").append(names);
        }
        return output.toString().trim();
    }

    public boolean checkAndAddContributor(Set<Contributor> contributors, int contributorCount) {
        AtomicBoolean isContributorAdded = new AtomicBoolean(false);
        this.getRoles().forEach((key, value) -> value.getContributors().clear());
        for(Contributor contributor : contributors) {
            contributor.getSkills().entrySet().stream().filter(entrySet ->
                    this.getRoles().containsKey(entrySet.getKey())
                    && (
                            entrySet.getValue() >= this.getRoles().get(entrySet.getKey()).getLevel()
                            || (
                                    (entrySet.getValue() - 1) == this.getRoles().get(entrySet.getKey()).getLevel()
                                    && hasMentor(entrySet.getKey(), entrySet.getValue())
                            )
                    )
            ).forEach(entrySet -> {
                Contributor tempContributor = new Contributor(contributor);
                tempContributor.getSkills().entrySet().removeIf(skill -> !skill.getKey().equalsIgnoreCase(entrySet.getKey()));
                this.getRoles().get(entrySet.getKey()).addContributor(tempContributor);
                isContributorAdded.set(true);
            });
        }
        return isContributorAdded.get();
    }

    public long getDelta() {
        return delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", score=" + score +
                ", bestBeforeDays=" + bestBeforeDays +
                ", roles=" + roles +
                '}';
    }

    @Override
    public int compareTo(Project o) {
        if(this.delta > o.getDelta()) {
            return 1;
        }
        return 0;
    }
}

public class Solution1 {

    private static int test;
    private static Set<Contributor> contributors;
    private static int contributorsCount;
    private static Set<Project> projects;
    private static int projectsCount;
    private static String output = "";

    private static void Input() throws Exception {
        Scanner scan = new Scanner(System.in);
        contributorsCount = scan.nextInt();
        contributors = new LinkedHashSet<>(contributorsCount);
        projectsCount = scan.nextInt();
        projects = new LinkedHashSet<>(projectsCount);
        for(int i = 0; i<contributorsCount; i ++) {
            Contributor contributor = new Contributor();
            contributor.takeInput(scan);
            contributors.add(contributor);
        }
        for(int i = 0; i<projectsCount; i ++) {
            Project project = new Project();
            project.takeInput(scan);
            project.checkAndAddContributor(contributors, contributorsCount);
            projects.add(project);
        }
        scan.close();
    }

    private static Project getNextProject() {
        long totalDuration = projects.stream().filter(Project::isCompleted).collect(Collectors.toList()).stream().mapToLong(Project::getDuration).sum();
        List<Project> nonCompletedProject = projects.stream().filter(project ->
                !project.isCompleted()
                && (
                    totalDuration < project.getBestBeforeDays()
                    || (totalDuration - project.getBestBeforeDays()) < project.getDuration()
                )
                && project.getRoles().values().stream().anyMatch(role -> !role.getContributors().isEmpty())
        ).collect(Collectors.toList());
        Collections.sort(nonCompletedProject);
        if(nonCompletedProject.isEmpty()) {
            return null;
        }
        return nonCompletedProject.get(0);
    }

    private static void Process() throws Exception {
        Project nextProject = getNextProject();
        while(nextProject != null) {
            if(nextProject.checkAndAddContributor(contributors, contributorsCount)) {
                nextProject.getRoles().forEach((name, role) -> {
                    role.getContributors().forEach(contributor -> {
                        contributors.stream().filter(con -> con.getName().equalsIgnoreCase(contributor.getName())).forEach(con -> con.upgradeSkill(name, role.getLevel()));
                    });
                });
                nextProject.setCompleted(true);
            }
            nextProject = getNextProject();
        }

//        for(Project project : projects) {
//            project.checkAndAddContributor(contributors, contributorsCount);
//        }
//
//        for(Contributor contributor : contributors) {
//            System.out.println(contributor);
//        }
//        for(Project project : projects) {
//            System.out.println(project);
//        }
    }

    private static void Output() throws Exception {
        List<Project> completedProjectList = projects.stream().filter(Project::isCompleted).collect(Collectors.toList());
        StringBuilder output = new StringBuilder("");
        output.append(completedProjectList.size());
        for(Project project : completedProjectList) {
            output.append("\n").append(project.getInfoForOutput());
        }
        System.out.println(output.toString().trim());
    }

    public static void main(String[] args) throws Exception {
        Input();
        Process();
        Output();
    }
}