package gregtechmod.api.recipe;

import gregtechmod.api.enums.GT_Items;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.api.util.GT_Utility;

import java.util.*;
import java.util.Map.Entry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * This File contains the functions used for Recipes. Please do not include this File AT ALL in your Moddownload as it ruins compatibility
 * This is just the Core of my Recipe System, if you just want to GET the Recipes I add, then you can access this File.
 * Do NOT add Recipes using the Constructors inside this Class, The GregTech_API File calls the correct Functions for these Constructors.
 * 
 * I know this File causes some Errors, because of missing Main Functions, but if you just need to compile Stuff, then remove said erroreous Functions.
 */
public class Recipe {
	public static volatile int VERSION = 408;
	
	/** It is an IdentityHashMap, because it uses a List as Key, and since that List changes (and therefore the Result of the equals Method), the Key is not secure, while the Identity is. */
	private static final IdentityHashMap<List<Recipe>, HashMap<Integer, List<Recipe>>> sRecipeMappings = new IdentityHashMap<List<Recipe>, HashMap<Integer, List<Recipe>>>();
	
	public static void reInit() {
//        GT_Log.log.info("GT_Mod: Re-Unificating Recipes.");
//        for (Entry<List<GT_Recipe>, HashMap<Integer, List<GT_Recipe>>> tMapEntry : sRecipeMappings.entrySet()) {
//        	HashMap<Integer, List<GT_Recipe>> tMap = tMapEntry.getValue();
//        	if (tMap != null) tMap.clear();
//        	for (GT_Recipe tRecipe : tMapEntry.getKey()) {
//            	GT_OreDictUnificator.setStackArray(true, tRecipe.mInputs);
//            	GT_OreDictUnificator.setStackArray(true, tRecipe.mOutputs);
//            	if (tMap != null) tRecipe.addToMap(tMap);
//        	}
//        }
	}
	
	public ItemStack[][] mInputs;
	public ItemStack[] mOutputs;
	public int mDuration;
	public int mEUt;
	public int mStartEU;
	// Use this to just disable a specific Recipe, but the Config enables that already for every single Recipe.
	public boolean mEnabled = true;
	
	public ItemStack[][] getRepresentativeInputs() {
		ItemStack[][] copy = new ItemStack[mInputs.length][];
		System.arraycopy(mInputs, 0, copy, 0, mInputs.length);
		return copy;
	}
	
	public ItemStack[] getOutputs() {
		ItemStack[] copy = new ItemStack[mOutputs.length];
		System.arraycopy(mOutputs, 0, copy, 0, copy.length);
		return copy;
	}
	
	public boolean match(boolean decrease, ItemStack...machineInputs) {
		if (machineInputs != null && machineInputs.length > 0) {
			Map<ItemStack, ItemStack> decreaseMap = new HashMap<>();
			for (ItemStack[] validItems : mInputs) { 					// Iterating slots
				for (ItemStack validItem : validItems) {				// Iterating valid items for slot
					for (ItemStack machineStack : machineInputs) {		// Iterating machine's input
						if (isItemStackMatch(machineStack, validItem)) {
							decreaseMap.put(machineStack, validItem);
						} else {
							return false;
						}
					}
				}
			}
			
			if (decrease) for (Entry<ItemStack, ItemStack> e : decreaseMap.entrySet()) {
				e.getKey().stackSize -= e.getValue().stackSize;
			}
		} else return false;
		
		return true;
	}
	
	private boolean isItemStackMatch(ItemStack invStack, ItemStack recipeStack) {
		return invStack.getItem() == recipeStack.getItem() &&
				invStack.getItemDamage() == recipeStack.getItemDamage() &&
				(invStack.hasTagCompound() ? invStack.getTagCompound().equals(recipeStack.getTagCompound()) : true) &&
				invStack.stackSize >= recipeStack.stackSize;
	}
	
	private final void addToMap(HashMap<Integer, List<Recipe>> aMap) {
//		for (ItemStack tStack : mInputs) if (tStack != null) {
//			Integer tIntStack = GT_Utility.stackToInt(tStack);
//			List<GT_Recipe> tList = aMap.get(tIntStack);
//			if (tList == null) aMap.put(tIntStack, tList = new ArrayList<GT_Recipe>(2));
//			tList.add(this);
//		}
	}
	
	private final void addToLists(List<Recipe> aList) {
//		HashMap<Integer, List<GT_Recipe>> aMap = sRecipeMappings.get(aList);
//		if (aMap == null) sRecipeMappings.put(aList, aMap = new HashMap<Integer, List<GT_Recipe>>());
//		aList.add(this);
//		addToMap(aMap);
	}
	
	public static Recipe findEqualRecipe(boolean aShapeless, boolean aNotUnificated, List<Recipe> aList, ItemStack... aInputs) {
		if (aInputs.length < 1) return null;
		HashMap<Integer, List<Recipe>> tMap = sRecipeMappings.get(aList);
		if (aNotUnificated) GT_OreDictUnificator.setStackArray(true, aInputs);
		if (tMap == null) {
			for (Recipe tRecipe : aList) if (tRecipe.match(false, aInputs)) return tRecipe.mEnabled?tRecipe:null;
		} else {
			for (ItemStack tStack : aInputs) if (tStack != null) {
				aList = tMap.get(GT_Utility.stackToInt(tStack));
				if (aList != null) for (Recipe tRecipe : aList) if (tRecipe.match(false, aInputs)) return tRecipe.mEnabled?tRecipe:null;
				aList = tMap.get(GT_Utility.stackToWildcard(tStack));
				if (aList != null) for (Recipe tRecipe : aList) if (tRecipe.match(false, aInputs)) return tRecipe.mEnabled?tRecipe:null;
			}
		}
		return null;
	}
	
	public void checkCellBalance() {
//		if (!GregTech_API.SECONDARY_DEBUG_MODE || mInputs.length < 1) return;
//		
//		int tInputAmount  = GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(mInputs);
//		int tOutputAmount = GT_ModHandler.getCapsuleCellContainerCountMultipliedWithStackSize(mOutputs);
//		
//		if (tInputAmount < tOutputAmount) {
//			if (!Materials.Tin.contains(mInputs)) {
//				GT_Log.log.catching(new Exception());
//			}
//		} else if (tInputAmount > tOutputAmount) {
//			if (!Materials.Tin.contains(mOutputs)) {
//				GT_Log.log.catching(new Exception());
//			}
//		}
	}
	
	public static boolean addRecipe(List<Recipe> aList, boolean aShapeless, ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt, int aStartEU) {
		return addRecipe(aList, aShapeless, new Recipe(aInput1, aInput2, aOutput1, aOutput2, aOutput3, aOutput4, aDuration, aEUt, aStartEU));
	}
	
	public static boolean addRecipe(List<Recipe> aList, boolean aShapeless, Recipe aRecipe) {
//		if (findEqualRecipe(aShapeless, false, aList, aRecipe.mInputs) != null) return false;
//		aRecipe.addToLists(aList);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack[] applyOreDict(ItemStack stack) {
		if (stack != null) {
			int[] ids = OreDictionary.getOreIDs(stack);
			if (ids.length > 0) {
				Set<ItemStack> stacks = new HashSet<>();
				for (int i : ids) {
					stacks.addAll(OreDictionary.getOres(i));
				}
				
				return stacks.toArray(new ItemStack[stacks.size()]);
			}
		}
		
		return new ItemStack[] {stack};
	}
	
	private Recipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt, int aStartEU) {
//		aInput1  = GT_OreDictUnificator.get(true, aInput1);
//		aInput2  = GT_OreDictUnificator.get(true, aInput2);
//		aOutput1 = GT_OreDictUnificator.get(true, aOutput1);
//		aOutput2 = GT_OreDictUnificator.get(true, aOutput2);
//		aOutput3 = GT_OreDictUnificator.get(true, aOutput3);
//		aOutput4 = GT_OreDictUnificator.get(true, aOutput4);
//		/*
//		 * Wtf gregorious, what the purpose of this?
//		 */
//		if (aInput1 != null && aInput1.getItemDamage() != GregTech_API.ITEM_WILDCARD_DAMAGE) {
//			if (GT_Utility.areStacksEqual(aInput1, aOutput1)) {
//				if (aInput1.stackSize >= aOutput1.stackSize) {
//					aInput1.stackSize -= aOutput1.stackSize;
//					aOutput1 = null;
//				} else {
//					aOutput1.stackSize -= aInput1.stackSize;
//				}
//			}
//			if (GT_Utility.areStacksEqual(aInput1, aOutput2)) {
//				if (aInput1.stackSize >= aOutput2.stackSize) {
//					aInput1.stackSize -= aOutput2.stackSize;
//					aOutput2 = null;
//				} else {
//					aOutput2.stackSize -= aInput1.stackSize;
//				}
//			}
//			if (GT_Utility.areStacksEqual(aInput1, aOutput3)) {
//				if (aInput1.stackSize >= aOutput3.stackSize) {
//					aInput1.stackSize -= aOutput3.stackSize;
//					aOutput3 = null;
//				} else {
//					aOutput3.stackSize -= aInput1.stackSize;
//				}
//			}
//			if (GT_Utility.areStacksEqual(aInput1, aOutput4)) {
//				if (aInput1.stackSize >= aOutput4.stackSize) {
//					aInput1.stackSize -= aOutput4.stackSize;
//					aOutput4 = null;
//				} else {
//					aOutput4.stackSize -= aInput1.stackSize;
//				}
//			}
//		}
//		
//		if (aInput2 != null && aInput2.getItemDamage() != GregTech_API.ITEM_WILDCARD_DAMAGE) {
//			if (GT_Utility.areStacksEqual(aInput2, aOutput1)) {
//				assert aOutput1 != null;
//				if (aInput2.stackSize >= aOutput1.stackSize) {
//					aInput2.stackSize -= aOutput1.stackSize;
//					aOutput1 = null;
//				} else {
//					aOutput1.stackSize -= aInput2.stackSize;
//				}
//			}
//			if (GT_Utility.areStacksEqual(aInput2, aOutput2)) {
//				assert aOutput2 != null;
//				if (aInput2.stackSize >= aOutput2.stackSize) {
//					aInput2.stackSize -= aOutput2.stackSize;
//					aOutput2 = null;
//				} else {
//					aOutput2.stackSize -= aInput2.stackSize;
//				}
//			}
//			if (GT_Utility.areStacksEqual(aInput2, aOutput3)) {
//				assert aOutput3 != null;
//				if (aInput2.stackSize >= aOutput3.stackSize) {
//					aInput2.stackSize -= aOutput3.stackSize;
//					aOutput3 = null;
//				} else {
//					aOutput3.stackSize -= aInput2.stackSize;
//				}
//			}
//			if (GT_Utility.areStacksEqual(aInput2, aOutput4)) {
//				assert aOutput4 != null;
//				if (aInput2.stackSize >= aOutput4.stackSize) {
//					aInput2.stackSize -= aOutput4.stackSize;
//					aOutput4 = null;
//				} else {
//					aOutput4.stackSize -= aInput2.stackSize;
//				}
//			}
//		}
//		
//		for (byte i = 64; i > 1; i--) if (aDuration / i > 0) {
//			if (aInput1  == null || aInput1 .stackSize % i == 0)
//			if (aInput2  == null || aInput2 .stackSize % i == 0)
//			if (aOutput1 == null || aOutput1.stackSize % i == 0)
//			if (aOutput2 == null || aOutput2.stackSize % i == 0)
//			if (aOutput3 == null || aOutput3.stackSize % i == 0)
//			if (aOutput4 == null || aOutput4.stackSize % i == 0) {
//				if (aInput1  != null) aInput1 .stackSize /= i;
//				if (aInput2  != null) aInput2 .stackSize /= i;
//				if (aOutput1 != null) aOutput1.stackSize /= i;
//				if (aOutput2 != null) aOutput2.stackSize /= i;
//				if (aOutput3 != null) aOutput3.stackSize /= i;
//				if (aOutput4 != null) aOutput4.stackSize /= i;
//				aDuration /= i;
//			}
//		}
		
		if (aInput1 == null) {
			mInputs = new ItemStack [0][];
		} else if (aInput2 == null) {
			mInputs = new ItemStack[][]{ applyOreDict(aInput1) }; 
		} else {
			mInputs = new ItemStack[][] { applyOreDict(aInput1), applyOreDict(aInput2)};
		}
		
		mOutputs = new ItemStack[] {aOutput1, aOutput2, aOutput3, aOutput4};
		mDuration = aDuration;
		mStartEU = aStartEU;
		mEUt = aEUt;
		
//		checkCellBalance();
	}
	
	public Recipe(ItemStack aInput1, ItemStack aOutput1, int aStartEU, int aType) {
		this(aInput1, aOutput1, null, null, null, aStartEU, aType);
	}
	
	// aStartEU = EU per Liter! If there is no Liquid for this Object, then it gets multiplied with 1000!
	public Recipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aStartEU, int aType) {
		this(aInput1, null, aOutput1, aOutput2, aOutput3, aOutput4, 0, 0, Math.max(1, aStartEU));
		
		if (mInputs.length > 0 && aStartEU > 0) {
			switch (aType) {
			// Diesel Generator
			case 0:
				addToLists(RecipeMaps.sDieselFuels);
				break;
			// Gas Turbine
			case 1:
				addToLists(RecipeMaps.sTurbineFuels);
				break;
			// Thermal Generator
			case 2:
				addToLists(RecipeMaps.sHotFuels);
				break;
			// Plasma Generator
			case 4:
				addToLists(RecipeMaps.sPlasmaFuels);
				break;
			// Magic Generator
			case 5:
				addToLists(RecipeMaps.sMagicFuels);
				break;
			// Fluid Generator. Usually 3. Every wrong Type ends up in the Semifluid Generator
			default:
				addToLists(RecipeMaps.sDenseLiquidFuels);
				break;
			}
		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt, int aStartEU) {
		this(aInput1, aInput2, aOutput1, null, null, null, Math.max(aDuration, 1), aEUt, Math.max(Math.min(aStartEU, 160000000), 0));
		if (mInputs.length > 1 && findEqualRecipe(true, false, RecipeMaps.sFusionRecipes, mInputs) == null) {
			addToLists(RecipeMaps.sFusionRecipes);
		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration) {
		this(aInput1, aInput2, aOutput1, aOutput2, aOutput3, aOutput4, Math.max(aDuration, 1), 5, 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(false, false, sCentrifugeRecipes, mInputs) == null) {
//			addToLists(sCentrifugeRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt) {
		this(aInput1, aInput2, aOutput1, aOutput2, aOutput3, aOutput4, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(false, false, sElectrolyzerRecipes, mInputs) == null) {
//			addToLists(sElectrolyzerRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt) {
		this(aInput1, null, aOutput1, aOutput2, null, null, aDuration, aEUt, 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sLatheRecipes, mInputs[0]) == null) {
//			addToLists(sLatheRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, int aDuration, ItemStack aOutput1, int aEUt) {
		this(aInput1, null, aOutput1, null, null, null, aDuration, aEUt, 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sCutterRecipes, mInputs[0]) == null) {
//			addToLists(sCutterRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3) {
		this(aInput1, aInput2, aOutput1, aOutput2, aOutput3, null, 200*aInput1.stackSize, 30, 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(false, false, sSawmillRecipes, mInputs) == null) {
//			addToLists(sSawmillRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4) {
		this(aInput1, aInput2, aOutput1, aOutput2, aOutput3, aOutput4, 100*aInput1.stackSize, 120, 0);
//		if (mInputs.length > 0 && aInput2 != null && mOutputs[0] != null && findEqualRecipe(false, false, sGrinderRecipes, mInputs) == null) {
//			addToLists(sGrinderRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, int aCellAmount, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt) {
		this(aInput1, aCellAmount>0?GT_Items.Cell_Empty.get(Math.min(64, Math.max(1, aCellAmount))):null, aOutput1, aOutput2, aOutput3, aOutput4, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(false, false, sDistillationRecipes, mInputs) == null) {
//			addToLists(sDistillationRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt, int aLevel) {
		this(aInput1, aInput2, aOutput1, aOutput2, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), aLevel > 0 ? aLevel : 100);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sBlastRecipes, mInputs) == null) {
//			addToLists(sBlastRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2) {
		this(aInput1, GT_ModHandler.getIC2Item("industrialTnt", aInput2>0?aInput2<64?aInput2:64:1, new ItemStack(Blocks.tnt, aInput2>0?aInput2<64?aInput2:64:1)), aOutput1, aOutput2, null, null, 20, 30, 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(false, false, sImplosionRecipes, mInputs) == null) {
//			addToLists(sImplosionRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, int aEUt, int aDuration, ItemStack aOutput1) {
		this(aInput1, null, aOutput1, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sWiremillRecipes, mInputs[0]) == null) {
//			addToLists(sWiremillRecipes);
//		}
	}
	
	public Recipe(int aEUt, int aDuration, ItemStack aInput1, ItemStack aOutput1) {
		this(aInput1, GT_Items.Circuit_Integrated.getWithDamage(0, aInput1.stackSize), aOutput1, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(false, false, sBenderRecipes, mInputs) == null) {
//			addToLists(sBenderRecipes);
//		}
	}
	
	public Recipe(int aEUt, int aDuration, ItemStack aInput1, ItemStack aShape, ItemStack aOutput1) {
		this(aInput1, aShape, aOutput1, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
//		if (mInputs.length > 1 && mOutputs[0] != null && findEqualRecipe(false, false, sExtruderRecipes, mInputs) == null) {
//			addToLists(sExtruderRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, int aEUt, ItemStack aInput2, int aDuration, ItemStack aOutput1) {
		this(aInput1, aInput2, aOutput1, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sAssemblerRecipes, mInputs) == null) {
//			addToLists(sAssemblerRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aInput2, int aEUt, int aDuration, ItemStack aOutput1) {
		this(aInput1, aInput2, aOutput1, null, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sAlloySmelterRecipes, mInputs) == null) {
//			addToLists(sAlloySmelterRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, int aEUt, ItemStack aInput2, int aDuration, ItemStack aOutput1, ItemStack aOutput2) {
		this(aInput1, aInput2, aOutput1, aOutput2, null, null, Math.max(aDuration, 1), Math.max(aEUt, 1), 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sCannerRecipes, mInputs) == null) {
//			addToLists(sCannerRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aOutput1, int aDuration) {
		this(aInput1, null, aOutput1, null, null, null, Math.max(aDuration, 1), 120, 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sVacuumRecipes, mInputs[0]) == null) {
//			addToLists(sVacuumRecipes);
//		}
	}
	
	public Recipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration) {
		this(aInput1, aInput2, aOutput1, null, null, null, Math.max(aDuration, 1), 30, 0);
//		if (mInputs.length > 0 && mOutputs[0] != null && findEqualRecipe(true, false, sChemicalRecipes, mInputs) == null) {
//			addToLists(sChemicalRecipes);
//		}
	}
}