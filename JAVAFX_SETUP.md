# JavaFX Setup Guide

## Setup Options

### Option 1: Use Java 11+ with Bundled JavaFX
Some JDK distributions include JavaFX:
- **Azul Zulu FX** - https://www.azul.com/downloads/?package=jdk-fx
- **Liberica Full JDK** - https://bell-sw.com/pages/downloads/

Download and install one of these, then:
```bash
cd src
javac AppGUI.java Student.java StudentManager.java StudentException.java
java AppGUI
```

---

### Option 2: Download JavaFX SDK Separately
1. Download JavaFX SDK: https://gluonhq.com/products/javafx/
2. Extract to a folder (e.g., `C:\javafx-sdk-21`)
3. Run with module path:

```bash
cd src
javac --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls *.java
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls AppGUI
```

---

### Option 3: Use Maven (Recommended for Production)
Create `pom.xml`:
```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.student</groupId>
  <artifactId>records-manager</artifactId>
  <version>1.0</version>
  
  <properties>
    <javafx.version>21</javafx.version>
  </properties>
  
  <dependencies>
    <dependency>
      <groupId>org.openjfx</groupId>
      <artifactId>javafx-controls</artifactId>
      <version>${javafx.version}</version>
    </dependency>
  </dependencies>
  
  <build>
    <plugins>
      <plugin>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-maven-plugin</artifactId>
        <version>0.0.8</version>
        <configuration>
          <mainClass>AppGUI</mainClass>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

Then run:
```bash
mvn clean javafx:run
```

---

### Option 4: VS Code Configuration 
A `.vscode/launch.json` file has been created with two configurations:
1. **Launch Console App** - Runs App.java (no JavaFX needed)
2. **Launch JavaFX GUI** - Runs AppGUI.java (requires JavaFX)

**To customize the JavaFX path:**
1. Open `.vscode/launch.json`
2. Find the line with `"vmArgs"`
3. Replace `C:\\javafx-sdk-21\\lib` with your actual JavaFX SDK path
4. Save and press **F5** to run

**To select which version to run:**
- Press **F5** or click the Run icon
- Click the dropdown next to the play button
- Choose "Launch Console App" or "Launch JavaFX GUI"

Example paths:
- Windows: `C:\\javafx-sdk-21\\lib`
- Mac: `/Library/Java/javafx-sdk-21/lib`
- Linux: `/opt/javafx-sdk-21/lib`

---

## Verification
Once JavaFX is set up, you should see:
- Window with "STUDENT RECORDS MANAGER" title
- Table showing 3 sample students
- 8 action buttons on the right
- Status bar at bottom

---

## Features in GUI Version
All console features are preserved:
- Add Student (auto-generated ID)
- Search by ID
- Search by Last Name (RECURSIVE)
- Update Student
- Remove Student (with confirmation)
- Sort (ID, Name, GPA)
- Statistics (with RECURSIVE GPA counting)
- Full input validation
- Error handling with dialogs

---

## If You Get Stuck
The console version (`App.java`) still works perfectly without JavaFX
```bash
javac App.java Student.java StudentManager.java StudentException.java
java App
```

