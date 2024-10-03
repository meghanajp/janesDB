<script>
  import svelteLogo from './assets/svelte.svg'
  import HomeScreen from './lib/customScreens/HomeScreen.svelte';
  import viteLogo from '/vite.svg'
   import {Content,ContentSwitcher, Switch, HeaderUtilities, Button} from "carbon-components-svelte"
   import {Information } from "carbon-icons-svelte"
   import {plan,plans}  from "../store"
  import Live from './lib/Live/Live.svelte';
  import Offline from './lib/offline/Offline.svelte';
  import HeaderAction from './UIShell/HeaderAction.svelte';
  // import Counter from './lib/Counter.svelte'

  plan.set($plans[0])
  let isOpen2 = false;

  const imports = {
		info:  {
        component: () => import('../src/lib/customScreens/PanelWidget.svelte'),
        ariaLabel: "InfoPanel",
        icon: Information,
        isopen: isOpen2,
        show: true
    }
  }

  // plan.update(value => value === 'Live' ? 'Offline' : 'Live');

  const handleMapSwitch = ()=>{
    plan.set("Live")
    console.log("live",$plan)
  }

  const handleCameraSwitch= ()=>{
    plan.set("Offline")
    console.log("offline",$plan)
  }



</script>

<main class="main">
  <div class="container">

    <header class="sticky-header">
    <div class="other-content">
      <HeaderUtilities>
        {#each Object.keys(imports) as key}
        {#if imports[key].show===true}
        <HeaderAction aria-label={imports[key].ariaLabel} icon={imports[key].icon} isOpen={imports[key].isopen}>
            {#await imports[key].component() then module}
            <!-- <TooltipDefinition  tooltipText={key}> -->
                <svelte:component this={module.default}/>
              <!-- </TooltipDefinition> -->
              <!-- {#if imports[key]==="logout"}
              <TooltipDefinition  tooltipText={key}></TooltipDefinition>
              {/if} -->
            {/await}
        </HeaderAction>
        
      
        {/if}
    {/each}
    </HeaderUtilities>
  </div>
</header>
    
      <div style="display:flex;">
        
        <Content class="view-selector">
            <!-- <CommonContainer> -->
              <!-- <RadioButtonGroup legendText="" bind:selected={$plan}>
                {#each $plans as value}
                  <RadioButton labelText={value} {value} />
                {/each}
              </RadioButtonGroup> -->

              <ContentSwitcher>
                <!-- {#each $plans as value} -->
                <Switch selected={$plan === 'Live'} on:click={handleMapSwitch}  text="Live"/>
                <Switch selected={$plan === 'Offline'} on:click={handleCameraSwitch}  text="Offline" />
                  <!-- {#if value==='Map'}
                <Switch selected={value}><Map size={16} /><span style="margin-left:10px;text-align:center">Map</span></Switch>
                {:else}
                <Switch selected={value}><CameraAction  size={16}/><span style="margin-left:10px;text-align:center">Camera</span></Switch>
                {/if} -->
                <!-- {/each} -->
                
              </ContentSwitcher>
              
          <!-- </CommonContainer> -->
      </Content>

    </div>
  </div>
  {#if $plan == "Live"}
      <div style="height:100vh;width:100%">
        <Live/>
      </div>
    {/if}
    {#if $plan == "Offline"}
    <div style="height:100vh;width:100%">
      <Offline/>
    </div>
  {/if}
</main>

<style>

:global(.bx--content){
  width: 10em;
    height:3em;
    font-size: 1.5rem;
}
  
    :global(.view-selector){
    display:flex;
    z-index:20;
    position:fixed;
    left: 0.5rem;
    top: 3.5rem;
    margin: 5px !important;
    padding: 0.5rem !important;
    border-radius: 5px;
  } 

  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    position: absolute;
    left: 50%;
  }

  .sticky-header{
   position: fixed;
   top: 0;
   width: 100%;
   background-color: #161616;
   /* background-color:rgba(0, 0, 0, 0.7); */
   color: beige;
   padding: 5px 0;
   z-index:1000;
   }


</style>
