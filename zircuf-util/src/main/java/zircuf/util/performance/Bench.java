package zircuf.util.performance;

/**
 * 簡易パフォーマンス測定ユーティリティ。
 * <p>
 * 「方式 A と B のどちらが速いか」を手早く比較する用途を想定した軽量ベンチマーククラス。
 * JIT ウォームアップを最低限行い、測定対象の最適化消失（Dead Code Elimination）を防ぐための
 * blackhole を備える。統計処理や GC 制御などは行わないため、参考値として利用する。
 *
 * <h2>使用例</h2>
 * <pre>{@code
 * Bench.measure(() -> {
 *     Bench.blackhole = buildJson(data);
 * }, 10_000, 100_000)
 * .result(System.out::println);
 * }</pre>
 */
public final class Bench {

    /** 測定対象の結果を保持するための blackhole。 */
    public static volatile Object blackhole;

    private Bench() {}

    /**
     * 測定結果を保持し、後続処理（ログ出力など）を行うためのクラス。
     */
    public static final class Result {
        private final double millis;

        private Result(double millis) {
            this.millis = millis;
        }

        /** 測定結果（ミリ秒）を返す。 */
        public double millis() {
            return millis;
        }

        /**
         * 測定結果を受け取る Consumer に渡す。
         * <p>
         * 例: <pre>{@code
         * Bench.measure(...).result(System.out::println);
         * }</pre>
         */
        public Result result(java.util.function.DoubleConsumer consumer) {
            consumer.accept(millis);
            return this;
        }

        @Override
        public String toString() {
            return millis + " ms";
        }
    }

    /**
     * 測定対象の Runnable を指定回数実行し、経過時間（ミリ秒）を Result として返す。
     *
     * @param target 測定対象の処理
     * @param warmup ウォームアップ回数（例：10_000）
     * @param count  測定回数（例：100_000）
     * @return 測定結果を保持する Result
     */
    public static Result measure(Runnable target, int warmup, int count) {
        for (int i = 0; i < warmup; i++) {
            target.run();
        }

        long start = System.nanoTime();
        for (int i = 0; i < count; i++) {
            target.run();
        }
        double ms = (System.nanoTime() - start) / 1_000_000d;

        return new Result(ms);
    }
}
