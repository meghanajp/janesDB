
<script>
    import {plan,plans} from "../../../store"
  import Header from "../../UIShell/Header.svelte";
  import Live from "../Live/Live.svelte";
  import Offline from "../offline/Offline.svelte";
  import {Content,ContentSwitcher, Switch} from "carbon-components-svelte"


//   plan.set($plans[0])

  const handleMapSwitch = ()=>{
    plan.set("Live")
  }

  const handleCameraSwitch= ()=>{
    plan.set("Offline")
  }


</script>

<main class="main">
<!-- <div class="container"> -->
    <div class="container">

  <div style="display:flex;">
        
    <Content class="view-selector">
          <ContentSwitcher>
            <Switch selected={$plan === 'Live'}  on:click={handleMapSwitch} text="Live"/>
            <Switch selected={$plan === 'Offline'}  on:click={handleCameraSwitch} text="Offline"/>
          </ContentSwitcher>
    </Content>
  </div>
  <!-- {#if $plan == "Live"}
<div style="height:100vh;width:100%"><Live/></div>

{/if}
{#if $plan == "Offline"}
<div style="height:100vh;width:100%"><Offline/></div>

{/if} -->

<!-- </div> -->

</div>
</main>

<style>
    

</style>