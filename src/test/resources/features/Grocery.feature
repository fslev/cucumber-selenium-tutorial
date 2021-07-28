@ui
@local @prod
@groceryList
Feature: Grocery List feature

  Scenario: Add grocery item
    * Go to grocery list tab
    * Check grocery list size=6
    * var itemName="Custom grocery item1"
    * Add grocery item with name=#[itemName]
    * Check grocery list size=7
    * Check grocery list contains item with name=#[itemName]

  Scenario: Remove grocery item
    * Go to grocery list tab
    * Check grocery list size=6
    * var itemName1="Baking item1"
    * var itemName2="Baking item5"
    * Remove grocery item with name=#[itemName1]
    * Remove grocery item with name=#[itemName2]
    * Check grocery list size=4
    * Check grocery list does not contain item with name=#[itemName1]
    * Check grocery list does not contain item with name=#[itemName2]

  Scenario: Add and remove grocery item
    * Go to grocery list tab
    * Check grocery list size=6
    * var itemName="Custom grocery item1"
    * Add grocery item with name=#[itemName]
    * Check grocery list size=7
    * Check grocery list contains item with name=#[itemName]
    * var itemName1="Baking item1"
    * var itemName2="Baking item5"
    * Remove grocery item with name=#[itemName1]
    * Remove grocery item with name=#[itemName2]
    * Check grocery list size=5
    * Check grocery list does not contain item with name=#[itemName1]
    * Check grocery list does not contain item with name=#[itemName2]
    # Remove remaining elements
    * Remove grocery item with name=#[itemName]
    * Remove grocery item with name=Baking item2
    * Remove grocery item with name=Baking item3
    * Remove grocery item with name=Baking item4
    * Remove grocery item with name=Baking item6
    * Check grocery list size=0
    * Check grocery list does not contain item with name=Baking item6
    # Add another item
    * Add grocery item with name=some item
    * Check grocery list size=1
    * Check grocery list contains item with name=some item






