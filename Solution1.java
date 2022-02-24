import java.util.*;

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

    public void upgradeSkill(String skill) {
        this.skills.put(skill, this.skills.get(skill) + 1);
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

class Project {
    private String name;
    private long duration;
    private int score;
    private int bestBeforeDays;
    private Map<String, Role> roles;

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
        int rolesCount = scanner.nextInt();
        this.roles = new LinkedHashMap<>(rolesCount);
        for(int i =0; i<rolesCount; i++) {
            String roleName = scanner.next();
            int level = scanner.nextInt();
            this.roles.put(roleName, new Role(level));
        }
    }

    public void checkAndAddContributor(Set<Contributor> contributors, int contributorCount) {
        for(Contributor contributor : contributors) {
            contributor.getSkills().entrySet().stream().filter(entrySet -> this.getRoles().containsKey(entrySet.getKey())).forEach(entrySet -> {
                Contributor tempContributor = new Contributor(contributor);
                tempContributor.getSkills().entrySet().removeIf(skill -> !skill.getKey().equalsIgnoreCase(entrySet.getKey()));
                this.getRoles().get(entrySet.getKey()).addContributor(tempContributor);
            });
        }
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
            projects.add(project);
        }
        scan.close();
    }

    private static void Process() throws Exception {
        //TODO: Type logic here

        for(Project project : projects) {
            project.checkAndAddContributor(contributors, contributorsCount);
        }

        for(Contributor contributor : contributors) {
            System.out.println(contributor);
        }
        for(Project project : projects) {
            System.out.println(project);
        }
    }

    private static void Output() throws Exception {
        System.out.println(output);
    }

    public static void main(String[] args) throws Exception {
        Input();
        Process();
        Output();
    }
}
