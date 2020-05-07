package analyzer.analysis.linked;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import analyzer.analysis.dangling.ScriptDictionaryAnalyzer;
import com.naru.uclair.draw.effect.BlinkEffect;
import com.naru.uclair.draw.effect.ColorChangeEffect;
import com.naru.uclair.draw.effect.DragEffect;
import com.naru.uclair.draw.effect.Effect;
import com.naru.uclair.draw.effect.EffectList;
import com.naru.uclair.draw.effect.EmergeEffect;
import com.naru.uclair.draw.effect.FillEffect;
import com.naru.uclair.draw.effect.MoveEffect;
import com.naru.uclair.draw.effect.TagValueEffect;
import com.naru.uclair.draw.effect.TouchEffect;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: WindowObjectTagLinkResult
 * @변경이력 	: 
 ************************************************/

public class WindowObjectTagLinkResult implements ObjectTagLinkResult {

	private EffectList effectList;
	private String windowName;
	private String figureId;
	private static final String NOT_APPLICABLE = "N/A";
	private static final String TAG_SEPARATOR = " / ";

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getWindowName()
	 */
	@Override
	public String getWindowName() {
		return this.windowName;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#setWindowName(java.lang.String)
	 */
	@Override
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getFigureId()
	 */
	@Override
	public String getFigureId() {
		// TODO Auto-generated method stub
		return this.figureId;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#setFigureId(java.lang.String)
	 */
	@Override
	public void setFigureId(String figureId) {
		this.figureId = figureId;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getEmergeTag()
	 */
	@Override
	public String getEmergeTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		EmergeEffect emergeEffect = (EmergeEffect) effectList.getEffect(Effect.EMERGE_IDX);
		if(null != emergeEffect) {
			Set<String> tags = emergeEffect.getLinkTags();
			if(null == tags) return NOT_APPLICABLE;
			if(tags.size() == 0) return NOT_APPLICABLE;
			
			StringBuffer buffer = new StringBuffer();
			for(String tag : tags) {
				buffer.append(tag);
				buffer.append(TAG_SEPARATOR);
			}
			return buffer.substring(0, buffer.length() - TAG_SEPARATOR.length()).toString();
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getBlinkTag()
	 */
	@Override
	public String getBlinkTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		BlinkEffect blinkEffect = (BlinkEffect) effectList.getEffect(Effect.BLINK_IDX);
		if(null != blinkEffect) {
			Set<String> tags = blinkEffect.getLinkTags();
			if(null == tags) return NOT_APPLICABLE;
			if(tags.size() == 0) return NOT_APPLICABLE;
			
			StringBuffer buffer = new StringBuffer();
			for(String tag : tags) {
				buffer.append(tag);
				buffer.append(TAG_SEPARATOR);
			}
			return buffer.substring(0, buffer.length() - TAG_SEPARATOR.length()).toString();
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getHorizontalMoveTag()
	 */
	@Override
	public String getHorizontalMoveTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		MoveEffect moveEffect = (MoveEffect) effectList.getEffect(Effect.MOVE_IDX);
		if(null != moveEffect) {
			return (null != moveEffect.getHorizontalTagKey() 
					? moveEffect.getHorizontalTagKey() : NOT_APPLICABLE);
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getVerticalMoveTag()
	 */
	@Override
	public String getVerticalMoveTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		MoveEffect moveEffect = (MoveEffect) effectList.getEffect(Effect.MOVE_IDX);
		if(null != moveEffect) {
			return (null != moveEffect.getVerticalTagKey() 
					? moveEffect.getVerticalTagKey() : NOT_APPLICABLE);
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getHorizontalDragTag()
	 */
	@Override
	public String getHorizontalDragTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		DragEffect dragEffect = (DragEffect) effectList.getEffect(Effect.DRAG_IDX);
		if(null != dragEffect) {
			return (null != dragEffect.getHorizontalTagKey() 
					? dragEffect.getHorizontalTagKey() : NOT_APPLICABLE);
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getVerticalDragTag()
	 */
	@Override
	public String getVerticalDragTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		DragEffect dragEffect = (DragEffect) effectList.getEffect(Effect.DRAG_IDX);
		if(null != dragEffect) {
			return (null != dragEffect.getVerticalTagKey() 
					? dragEffect.getVerticalTagKey() : NOT_APPLICABLE);
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getFillTag()
	 */
	@Override
	public String getHorizontalFillTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		FillEffect fillEffect = (FillEffect) effectList.getEffect(Effect.FILL_IDX);
		if(null != fillEffect) {
			return (null != fillEffect.getHorizontalTagKey() 
					? fillEffect.getHorizontalTagKey() : NOT_APPLICABLE);
		}
		
		return NOT_APPLICABLE;
	}
	
	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getFillTag()
	 */
	@Override
	public String getVerticalFillTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		FillEffect fillEffect = (FillEffect) effectList.getEffect(Effect.FILL_IDX);
		if(null != fillEffect) {
			return (null != fillEffect.getVerticalTagKey() 
					? fillEffect.getVerticalTagKey() : NOT_APPLICABLE);
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getColorChangeTag()
	 */
	@Override
	public String getColorChangeTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		ColorChangeEffect colorChangeEffect = (ColorChangeEffect) effectList.getEffect(Effect.COLORCHANGE_IDX);
		if(null != colorChangeEffect) {
			Set<String> tags = colorChangeEffect.getLinkTags();
			if(null == tags) return NOT_APPLICABLE;
			if(tags.size() == 0) return NOT_APPLICABLE;
			
			StringBuffer buffer = new StringBuffer();
			for(String tag : tags) {
				buffer.append(tag);
				buffer.append(TAG_SEPARATOR);
			}
			return buffer.substring(0, buffer.length() - TAG_SEPARATOR.length()).toString();
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getTagDisplayTag()
	 */
	@Override
	public String getTagDisplayTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		TagValueEffect tagValueEffect = (TagValueEffect) effectList.getEffect(Effect.TAGDISPLAY_IDX);
		if(null != tagValueEffect) {
			return (null != tagValueEffect.getTagKey() 
					? tagValueEffect.getTagKey() : NOT_APPLICABLE);
		}
		
		return NOT_APPLICABLE;
	}

	/* (non-Javadoc)
	 * @see com.naru.uclair.analyzer.analysis.linked.ObjectTagLinkResult#getTouchTag()
	 */
	@Override
	public String getTouchTag() {
		if(null == effectList) return NOT_APPLICABLE;
		
		TouchEffect touchEffect = (TouchEffect) effectList.getEffect(Effect.TOUCH_IDX);
		if(null != touchEffect) {
			// 제어 태그 확인.
			HashSet<String> tags = new HashSet<String>();
			String controlTagKey = touchEffect.getTagKey();
			if(null != controlTagKey) {
				tags.add(controlTagKey);
			}
			
			// 스크립트 확인.
			String scriptCode = touchEffect.getScript();
			if(null != scriptCode) {
				List<String> tagList = ScriptDictionaryAnalyzer.scriptTagGenerate(scriptCode);
				if(null != tagList) {
					tags.addAll(tagList);
				}
			}
			
			if(tags.size() == 0) return NOT_APPLICABLE;
			
			StringBuffer buffer = new StringBuffer();
			for(String tag : tags) {
				buffer.append(tag);
				buffer.append(TAG_SEPARATOR);
			}
			return buffer.substring(0, buffer.length() - TAG_SEPARATOR.length()).toString();
		}
		
		return NOT_APPLICABLE;
	}
	
	public void setEffectList(EffectList effectList) {
		this.effectList = effectList;
	}
	
	public boolean isEmpty() {
		boolean isEmpty = NOT_APPLICABLE.equals(getEmergeTag()) 
				&& NOT_APPLICABLE.equals(getBlinkTag()) 
				&& NOT_APPLICABLE.equals(getHorizontalMoveTag())  
				&& NOT_APPLICABLE.equals(getVerticalMoveTag()) 
				&& NOT_APPLICABLE.equals(getHorizontalDragTag()) 
				&& NOT_APPLICABLE.equals(getVerticalDragTag()) 
				&& NOT_APPLICABLE.equals(getHorizontalFillTag()) 
				&& NOT_APPLICABLE.equals(getVerticalFillTag()) 
				&& NOT_APPLICABLE.equals(getColorChangeTag()) 
				&& NOT_APPLICABLE.equals(getTagDisplayTag())
				&& NOT_APPLICABLE.equals(getTouchTag()) ;
		return isEmpty;
	}

}
