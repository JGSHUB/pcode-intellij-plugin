-dontwarn **
-ignorewarnings
-dontoptimize
-dontshrink
-keepattributes Signature,StackMapTable,*Annotation*

-keep public class javiergs.vm.Interpreter {
    public *;
}
-keep public class javiergs.vm.Symbol {
    public *;
}
-keep public class javiergs.vm.Frame {
    public *;
}
-keep public class javiergs.vm.Instruction {
    public *;
}
-keep public class javiergs.vm.VmState {
    public *;
}
-keep public interface javiergs.vm.VmListener {
    public *;
}

-keep class javiergs.pcode.PCodeConsoleFactory { *; }
-keep class javiergs.pcode.PCodeFileType { *; }
-keep class javiergs.pcode.PCodeSyntaxHighlighterFactory { *; }
-keep class javiergs.pcode.RunPCodeAction { *; }
-keep class javiergs.pcode.StepPCodeAction { *; }
-keep class javiergs.pcode.PCodeFileEditorListener { *; }
-keep class javiergs.pcode.PCodeLanguage { *; }
-keep class javiergs.pcode.PCodeTokenType { *; }
-keep interface javiergs.pcode.PCodeTokenTypes { *; }