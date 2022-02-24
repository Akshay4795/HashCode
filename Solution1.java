import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

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

    @Override
    public String toString() {
        return "Contributor{" +
                "name='" + name + '\'' +
                ", skills=" + skills +
                '}';
    }
}

class Project {
    private String name;
    private long duration;
    private int score;
    private int bestBeforeDays;
    private Map<String, Integer> roles;

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

    public Map<String, Integer> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Integer> roles) {
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
            this.roles.put(roleName, level);
        }
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
    private static Contributor[] contributors;
    private static int contributorsCount;
    private static Project[] projects;
    private static int projectsCount;
    private static String output = "";

    private static void Input() throws Exception {
        Scanner scan = new Scanner(System.in);
        contributorsCount = scan.nextInt();
        contributors = new Contributor[contributorsCount];
        projectsCount = scan.nextInt();
        projects = new Project[projectsCount];
        for(int i = 0; i<contributorsCount; i ++) {
            contributors[i] = new Contributor();
            contributors[i].takeInput(scan);
        }
        for(int i = 0; i<projectsCount; i ++) {
            projects[i] = new Project();
            projects[i].takeInput(scan);
        }
        scan.close();
    }

    private static void Process() throws Exception {
        //TODO: Type logic here
        for(int i = 0; i<contributorsCount; i ++) {
            System.out.println(contributors[i]);
        }
        for(int i = 0; i<projectsCount; i ++) {
            System.out.println(projects[i]);
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
