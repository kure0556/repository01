package zircuf.env;

import java.time.Clock;
import java.time.Instant;

import zircuf.util.datetime.DT;
import zircuf.util.datetime.TimeProvider;

/**
 * システム時刻ラッパー。<br/>
 * <br/>
 * 【設計意図】<br/>
 * - java.time.Clock をラップして、DIで差し替え可能にする<br/>
 * - 業務では秒精度を標準とする（now）<br/>
 * - テストでは Clock を差し替えて時間固定できる<br/>
 * <br/>
 * 【使い分け】<br/>
 * - Spring環境：DI（@Component）で使用<br/>
 * - 非Spring環境：staticファクトリ(systemJst)で簡易生成<br/>
 */
public class SystemClock implements TimeProvider {

    /**
     * 非DI環境向けのデフォルトインスタンス（JST固定）。
     */
    private static final SystemClock JST_INSTANCE = new SystemClock(Clock.system(DT.Zone.JST));

    /**
     * 非DI環境向けファクトリ（JST固定）。
     */
    public static SystemClock systemJst() {
        return JST_INSTANCE;
    }

    /**
     * 時刻取得の実体。<br/>
     * <br/>
     * 【DIポイント】
     * - Springでは @Bean で定義された Clock が注入される<br/>
     * - テストでは Clock.fixed(...) に差し替える<br/>
     */
    private final Clock clock;

    /**
     * コンストラクタインジェクション。<br/>
     * <br/>
     * 【DIポイント】
     * - デフォルトコンストラクタは定義しない<br/>
     *   → Clockの注入を強制するため<br/>
     * - これにより「必ず差し替え可能」な設計になる<br/>
     */
    public SystemClock(Clock clock) {
        this.clock = clock;
    }

	@Override
	public Instant nowInstant() {
		return clock.instant();
	}
}