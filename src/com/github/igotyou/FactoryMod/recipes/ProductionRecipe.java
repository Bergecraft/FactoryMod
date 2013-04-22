package com.github.igotyou.FactoryMod.recipes;

import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;

import com.github.igotyou.FactoryMod.interfaces.Recipe;
import com.github.igotyou.FactoryMod.utility.ItemList;
import com.github.igotyou.FactoryMod.utility.NamedItemStack;
import org.bukkit.inventory.Inventory;

public class ProductionRecipe implements Recipe
{
	private String title;
	private String recipeName;
	private int productionTime;
	private ItemList<NamedItemStack> inputs;
	private ItemList<NamedItemStack> upgrades;
	private ItemList<NamedItemStack> outputs;
	private ItemList<NamedItemStack> repairs;
	private List<ProductionRecipe> outputRecipes;
	private List<ProbabilisticEnchantment> enchantments;
	private boolean useOnce;
	private int maintenance;
	int totalNumber;
	
	public ProductionRecipe(String title,String recipeName,int productionTime,ItemList<NamedItemStack> inputs,ItemList<NamedItemStack> upgrades,
		ItemList<NamedItemStack> outputs,List<ProbabilisticEnchantment> enchantments,boolean useOnce,int maintenance, ItemList<NamedItemStack> repairs)
	{
		this.title=title;
		this.recipeName = recipeName;
		this.productionTime = productionTime;
		this.inputs = inputs;
		this.upgrades=upgrades;
		this.outputs = outputs;
		this.outputRecipes=new ArrayList<ProductionRecipe>();
		this.enchantments=enchantments;
		this.useOnce=useOnce;
		this.maintenance=maintenance;
		this.totalNumber=0;
		this.repairs=repairs;
	}
	
	public ProductionRecipe(String title,String recipeName,int productionTime,ItemList<NamedItemStack> repairs)
	{
		this(title,recipeName,productionTime,new ItemList<>(),new ItemList<>(),new ItemList<>(),new ArrayList<ProbabilisticEnchantment>(),false,0,repairs);
	}
	
	public boolean hasMaterials(Inventory inventory)
	{
		return inputs.allIn(inventory)&&upgrades.oneIn(inventory)&&repairs.allIn(inventory);
	}
	public void addOutputRecipe(ProductionRecipe outputRecipe)
	{
		this.outputRecipes.add(outputRecipe);
	}

	public ItemList<NamedItemStack> getInputs()
	{
		return inputs;
	}
	
	public ItemList<NamedItemStack> getUpgrades()
	{
		return upgrades;
	}
	
	public ItemList<NamedItemStack> getOutputs() 
	{
		return outputs;
	}
	
	public ItemList<NamedItemStack> getRepairs()
	{
		return repairs;
	}

	public HashMap<Enchantment, Integer> getEnchantments()
	{
		HashMap<Enchantment, Integer> randomEnchantments = new HashMap<Enchantment, Integer>();
		Random rand = new Random();
		for(int i=0;i<enchantments.size();i++)
		{
			if(enchantments.get(i).getProbability()>=rand.nextDouble())
			{
				randomEnchantments.put(enchantments.get(i).getEnchantment(),enchantments.get(i).getLevel());
			}
		}
		return randomEnchantments;
	}
	
	public boolean hasEnchantments()
	{
		return enchantments.size()>0;
	}
	
	public String getTitle()
	{
		return title;
	}
	public String getRecipeName() 
	{
		return recipeName;
	}

	public int getProductionTime() 
	{
		return productionTime;
	}

	public List<ProductionRecipe> getOutputRecipes()
	{
		return outputRecipes;
	}

	public boolean getUseOnce()
	{
		return useOnce;
	}
	
	public int getMaintenance()
	{
		return maintenance;
	}
	
	public double degradeAmount()
	{
		return (1-Math.pow(.9,totalNumber))*maintenance;
	}
	public void setTotalNumber(int number)
	{
		totalNumber=number;
	}
	public void incrementCount()
	{
		totalNumber++;
	}
}
